package kr.co.rur.core.controller.member.request

data class LoginRequest(
    var code: String,
    var callbackUrl: String
)
