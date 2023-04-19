package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.global.exception.ForbiddenException
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import kr.hs.entrydsm.satellite.global.thirdparty.oauth.dto.response.GoogleAccessTokenResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

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
        .onStatus(HttpStatus::is4xxClientError) { Mono.error(ForbiddenException) }
        .bodyToMono(object : ParameterizedTypeReference<GoogleAccessTokenResponse>() {})
        .onErrorMap { throw it }
        .awaitSingleOrNull() ?: throw InternalServerError
}