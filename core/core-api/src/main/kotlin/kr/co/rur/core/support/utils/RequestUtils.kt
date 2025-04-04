package kr.co.rur.core.support.utils

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object RequestUtils {
    fun getRemoteIp(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        return request.remoteAddr
    }
}