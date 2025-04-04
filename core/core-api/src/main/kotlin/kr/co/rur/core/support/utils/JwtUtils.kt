package kr.co.rur.core.support.utils

import io.jsonwebtoken.*
import org.apache.commons.lang3.time.DateUtils
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object JwtUtils {

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
}