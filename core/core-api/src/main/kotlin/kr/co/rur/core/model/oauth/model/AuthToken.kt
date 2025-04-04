package kr.co.rur.core.model.oauth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthToken(
    @JsonProperty("access_token")
    val accessToken: String? = null,

    @JsonProperty("token_type")
    val tokenType: String? = null,

    @JsonProperty("refresh_token")
    val refreshToken: String? = null,

    @JsonProperty("expires_in")
    var expiresIn: Int? = null,

    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int? = null
)
