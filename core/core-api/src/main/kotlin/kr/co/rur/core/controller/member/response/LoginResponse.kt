package kr.co.rur.core.controller.member.response

data class LoginResponse(
    var accessToken: String,
    var refreshToken: String,
)
