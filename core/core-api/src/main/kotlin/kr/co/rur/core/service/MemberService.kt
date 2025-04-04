package kr.co.rur.core.service

import kr.co.rur.core.controller.member.request.LoginRequest
import kr.co.rur.core.service.oauth.KakaoSnsService
import kr.co.rur.storage.db.core.entity.MemberEntity
import kr.co.rur.storage.db.core.repository.MemberEntityRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class MemberService(
    private val memberEntityRepository : MemberEntityRepository,
    private val kakaoSnsService: KakaoSnsService
) {

    fun getAuthPath() : String {
        return kakaoSnsService.getAuthPath()
    }

    fun login(loginRequest: LoginRequest): String {

        // sns id 가져오기
        var snsId = kakaoSnsService.getSnsId(loginRequest)
        println(snsId)
        // sns id 로 가입된 회원 찾기
        if (!StringUtils.hasLength(snsId)) {
            // TODO : error 처리
        }

        var memberEntity = memberEntityRepository.findBySnsId(snsId)

        return "";
    }
}