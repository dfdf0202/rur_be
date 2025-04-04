package kr.co.rur.core.domain.auth.login

data class AccessToken(
    var accessToken: String,
    var refreshToken: String,
)
