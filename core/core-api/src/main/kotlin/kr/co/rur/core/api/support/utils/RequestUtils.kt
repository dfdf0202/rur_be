package kr.co.rur.core.api.support.utils

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object RequestUtils {
    fun getRemoteIp(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        return request.remoteAddr
    }

    fun getId(): Long? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes

        if (requestAttributes != null) {
            val request = requestAttributes.request
            val memberId: Long = request.getAttribute("accountByToken").toString().toLong() ?: return null
            return memberId
        }
        return null
    }
}