package kr.co.rur.core.api.model.oauth.conf

data class AuthConfig (
    var code: String? = null,
    var redirectUri: String? = null,
)