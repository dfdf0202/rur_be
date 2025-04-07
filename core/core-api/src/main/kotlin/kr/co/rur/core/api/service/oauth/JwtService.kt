package kr.co.rur.core.api.service.oauth

import kr.co.rur.core.api.domain.auth.login.Member
import kr.co.rur.core.api.model.oauth.model.AuthToken
import kr.co.rur.core.api.support.utils.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtService(
    @Value("\${jwt.secretKey}")
    private var secretKey: String,
) {

    fun makeAccessToken(member: Member): AuthToken {
        var expireMinutes = (60 * 24 * 365)

        //token에 로그인 계정 정보 저장
        val claims: MutableMap<String, Any> = mutableMapOf(
            "memberId" to member.id!!,
        )

        var token = JwtUtils.generateToken(claims, expireMinutes, secretKey)
        var refreshToken = JwtUtils.generateToken(claims, expireMinutes * 24 * 30, secretKey)
        return AuthToken(token!!, null, refreshToken!!, null, null)
    }

}