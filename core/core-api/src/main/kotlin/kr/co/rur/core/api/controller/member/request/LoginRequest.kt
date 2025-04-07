package kr.co.rur.core.api.controller.member.request

data class LoginRequest(
    var code: String,
    var callbackUrl: String
)
