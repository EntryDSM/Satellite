package kr.hs.entrydsm.satellite.global.thirdparty.oauth.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleAccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
)