package kr.co.rur.core.api.domain.auth.login

data class AccessToken(
    var accessToken: String,
    var refreshToken: String,
)
