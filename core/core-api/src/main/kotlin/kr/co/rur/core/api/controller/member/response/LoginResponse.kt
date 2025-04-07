package kr.co.rur.core.api.controller.member.response

data class LoginResponse(
    var id: Long,
    var isMemberGroup: Boolean,
    var accessToken: String,
    var refreshToken: String,
)
