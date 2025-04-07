package kr.co.rur.core.api.webfilter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.rur.core.api.support.utils.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AccessTokenWebFilter(
    @Value("\${jwt.secretKey}")
    private var secretKey: String,
) : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    )  {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val path = httpRequest.requestURI

        if (!shouldFilter(path)) {
            chain.doFilter(request, response)
            return
        }

        try {
            JwtUtils.checkToken(httpRequest, secretKey)
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalArgumentException("Token invalid")
        }

        chain.doFilter(request, response)
    }

    private fun shouldFilter(path: String): Boolean {
        val includePrefix = "/api/member"

        val exactExcludePaths = setOf(
            "/api/member/auth-path",
            "/api/member/login"
        )

        return path.startsWith(includePrefix) && !exactExcludePaths.contains(path)
    }
}