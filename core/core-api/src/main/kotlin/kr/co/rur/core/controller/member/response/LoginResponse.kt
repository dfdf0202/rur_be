package kr.co.rur.core.controller.member.response

data class LoginResponse(
    var id: Long,
    var isMemberGroup: Boolean,
    var accessToken: String,
    var refreshToken: String,
)
