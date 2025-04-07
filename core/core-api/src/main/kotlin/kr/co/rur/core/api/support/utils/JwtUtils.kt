package kr.co.rur.core.api.support.utils

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateUtils
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object JwtUtils {

    fun getAccessToken(request: HttpServletRequest): String? {
        val accessToken = request.getHeader("accessToken")
        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken
        }
        return null
    }

    fun parseToken(token: String?, secretKey: String): Claims {
        var claims: Claims = Jwts.claims()
        try {

            claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)) //                    .requireAudience(getIp(request))
                .build()
                .parseClaimsJws(token)
                .getBody()
            claims.put("isCertifiedToken", java.lang.Boolean.TRUE)
            claims.put("isExpired", java.lang.Boolean.FALSE)
        } catch (ex: ExpiredJwtException) {
            claims.put("isCertifiedToken", java.lang.Boolean.TRUE)
            claims.put("isExpired", java.lang.Boolean.TRUE)
        } catch (ex: JwtException) {
            claims.put("isCertifiedToken", java.lang.Boolean.FALSE)
        } catch (ex: IllegalArgumentException) {
            claims.put("isCertifiedToken", java.lang.Boolean.FALSE)
        }
        return claims
    }

    fun generateToken(claims: Map<String, Any>, expireMinutes: Int, secretKey: String): String? {
        var token: String? = null
        try {
            val jwtBuilder = Jwts.builder()

            if (claims != null) {
                jwtBuilder.setClaims(claims)
            }

            jwtBuilder.setId(JwtUtils.getJwtId())

            if (expireMinutes > 0) {
                val expireDate: Date = DateUtils.addMinutes(Date(), expireMinutes)
                jwtBuilder.setExpiration(expireDate)
            }

            val secreKeyBytes = DatatypeConverter.parseBase64Binary(secretKey)
            jwtBuilder.signWith(
                SecretKeySpec(secreKeyBytes, SignatureAlgorithm.HS256.jcaName),
                SignatureAlgorithm.HS256
            )
            token = jwtBuilder.compact()
        } catch (ex: JwtException) {
            ex.printStackTrace()
        } catch (ex: java.lang.IllegalArgumentException) {
            ex.printStackTrace()
        }
        return token
    }

    private fun getJwtId(): String {
        return UUID.randomUUID().toString()
    }

    fun checkToken(request: HttpServletRequest, secretKey: String): Boolean {
        val accessToken: String = getAccessToken(request)!!
        if (StringUtils.isEmpty(accessToken)) {
            throw IllegalArgumentException("Access token invalid")
        }

        //유효시간내의 토큰 해석
        checkAccessToken(accessToken, secretKey, request)
        return true
    }

    fun checkAccessToken(accessToken: String?, secretKey: String, request: HttpServletRequest): Boolean {
        val parseToken: Claims = parseToken(accessToken, secretKey)
        val isCertifiedToken: Boolean = parseToken.get("isCertifiedToken").toString().toBoolean()
        if (!isCertifiedToken) {
            //accessToken is wrong
            throw IllegalArgumentException("Access token invalid")
        }
        val isExpired: Boolean = parseToken.get("isExpired").toString().toBoolean()
        if (isExpired) {
            // RefreshToken 요청
            throw IllegalArgumentException("Expire Access token")
        }

        // token 중에 저장된 유저정보 가져오기
        val accountByToken: Any = parseToken["memberId"]!!
        val objectMapper = ObjectMapper()

        //val account: MemberAccount = objectMapper.convertValue<MemberAccount>(parseToken, MemberAccount::class.java)

        if (accountByToken == null) {
            throw IllegalArgumentException("Account invalid")
        } else {
            // URL이 /mgt로 시작하면 ADMIN 권한 확인
            request.setAttribute("accountByToken", accountByToken)
        }
        return true
    }
}