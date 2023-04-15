package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kr.hs.entrydsm.satellite.global.exception.ForbiddenException
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import kr.hs.entrydsm.satellite.global.thirdparty.oauth.dto.response.GoogleEmailResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GoogleEmail(
    private val webClient: WebClient
) {
    fun getEmail(
        accessToken: String,
        alt: String = "json"
    ) = webClient.post()
        .uri {
            it.path("https://www.googleapis.com/oauth2/v1/userinfo")
                .queryParam("access_token", accessToken)
                .queryParam("alt", alt)
                .build()
        }.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError) { Mono.error(ForbiddenException) }
        .bodyToMono(object : ParameterizedTypeReference<GoogleEmailResponse>() {})
        .onErrorMap { throw it }
        .block() ?: throw InternalServerError
}