package kr.co.rur.core.model.oauth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoResponseWrapper(
    @JsonProperty("id")
    var id: String,

    @JsonProperty("connected_at")
    val connectedAt: String? = null
)
