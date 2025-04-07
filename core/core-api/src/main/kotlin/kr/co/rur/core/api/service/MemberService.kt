package kr.co.rur.core.api.service

import kr.co.rur.core.api.controller.member.request.LoginRequest
import kr.co.rur.core.api.controller.member.request.MemberGroupUpdateRequest
import kr.co.rur.core.api.controller.member.response.LoginResponse
import kr.co.rur.core.api.domain.auth.login.Member
import kr.co.rur.core.api.service.oauth.JwtService
import kr.co.rur.core.api.service.oauth.KakaoSnsService
import kr.co.rur.core.api.support.handler.RedisHandler
import kr.co.rur.core.api.support.utils.EncryptionUtils
import kr.co.rur.core.enum.GroupRole
import kr.co.rur.storage.db.core.entity.MemberEntity
import kr.co.rur.storage.db.core.entity.MemberGroupEntity
import kr.co.rur.storage.db.core.repository.GroupInfoEntityRepository
import kr.co.rur.storage.db.core.repository.MemberEntityRepository
import kr.co.rur.storage.db.core.repository.MemberGroupEntityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.LocalDateTime

@Service
class MemberService(
    private val memberEntityRepository : MemberEntityRepository,
    private val memberGroupEntityRepository: MemberGroupEntityRepository,
    private val groupInfoEntityRepository: GroupInfoEntityRepository,
    private val kakaoSnsService: KakaoSnsService,
    private val jwtService: JwtService,
    private val redisHandler: RedisHandler
) {

    fun getAuthPath() : String {
        return kakaoSnsService.getAuthPath()
    }

    fun login(loginRequest: LoginRequest): LoginResponse {

        // sns id 가져오기
        var snsId = kakaoSnsService.getSnsId(loginRequest)
        println(snsId)
        // sns id 로 가입된 회원 찾기
        if (!StringUtils.hasLength(snsId)) {
            // TODO : error 처리
        }

        var memberEntity = memberEntityRepository.findBySnsId(snsId)

        if (memberEntity == null) {
            memberEntity = MemberEntity(snsId)
            memberEntityRepository.save(memberEntity)
        }


        var isMemberGroup = memberGroupEntityRepository.findByMemberEntity(memberEntity).isNotEmpty()
        var member = Member(memberEntity.getId(), isMemberGroup)

        var authToken = jwtService.makeAccessToken(member)

        return LoginResponse(memberEntity.getId()!!, isMemberGroup, authToken.accessToken!!, authToken.refreshToken!!)
    }

    @Transactional
    fun updateMemberGroup(memberId: Long?, groupRequest: MemberGroupUpdateRequest) {
        var memberEntity = memberEntityRepository.findById(memberId!!)
            .orElseThrow { RuntimeException("Member entity not found") }

        memberEntity.updateNickNm(groupRequest.nickNm)
        memberEntityRepository.save(memberEntity)

        val groupId = redisHandler.get(groupRequest.code)?.toLong() ?: throw IllegalArgumentException("초대코드가 유효하지 않습니다.")

        var groupEntity = groupInfoEntityRepository.findById(groupId)
            .orElseThrow({throw IllegalArgumentException("초대코드가 유효하지 않습니다.")})

        var memberGroupEntity = MemberGroupEntity(memberEntity, groupEntity, GroupRole.MEMBER)
        memberGroupEntityRepository.save(memberGroupEntity)
    }
}