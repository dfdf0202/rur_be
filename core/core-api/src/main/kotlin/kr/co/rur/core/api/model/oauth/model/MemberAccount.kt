package kr.co.rur.core.api.model.oauth.model

data class MemberAccount (
    var memberId: Long? = null,
    var jti: String? = null,
    var isMemberGroup: Boolean = false,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)