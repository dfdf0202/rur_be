package kr.co.rur.core.service

import kr.co.rur.core.controller.member.request.LoginRequest
import kr.co.rur.core.controller.member.response.LoginResponse
import kr.co.rur.core.domain.auth.login.Member
import kr.co.rur.core.service.oauth.JwtService
import kr.co.rur.core.service.oauth.KakaoSnsService
import kr.co.rur.storage.db.core.entity.MemberEntity
import kr.co.rur.storage.db.core.repository.MemberEntityRepository
import kr.co.rur.storage.db.core.repository.MemberGroupEntityRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class MemberService(
    private val memberEntityRepository : MemberEntityRepository,
    private val memberGroupEntityRepository: MemberGroupEntityRepository,
    private val kakaoSnsService: KakaoSnsService,
    private val jwtService: JwtService,
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
}