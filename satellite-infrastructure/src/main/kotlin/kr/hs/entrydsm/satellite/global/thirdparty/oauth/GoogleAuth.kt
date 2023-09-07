package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.hs.entrydsm.satellite.global.exception.DynamicForbiddenException
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import kr.hs.entrydsm.satellite.global.thirdparty.oauth.dto.response.GoogleAccessTokenResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GoogleAuth {
    suspend fun queryAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String = "authorization_code",
    ) = WebClient.builder()
        .baseUrl("https://oauth2.googleapis.com/token").build()
        .post()
        .uri {
            it.queryParam("code", code)
                .queryParam("clientId", clientId)
                .queryParam("clientSecret", clientSecret)
                .queryParam("redirectUri", redirectUri)
                .queryParam("grantType", grantType)
                .build()
        }.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError) {
            it.bodyToMono(String::class.java)
                .map { body -> DynamicForbiddenException(body) }
        }
        .bodyToMono(object : ParameterizedTypeReference<GoogleAccessTokenResponse>() {})
        .onErrorMap { throw it }
        .awaitFirstOrNull() ?: throw InternalServerError
}