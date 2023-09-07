package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.hs.entrydsm.satellite.global.exception.DynamicForbiddenException
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import kr.hs.entrydsm.satellite.global.thirdparty.oauth.dto.response.GoogleEmailResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GoogleEmail {
    suspend fun getEmail(
        accessToken: String,
        alt: String = "json"
    ) = WebClient.builder()
        .baseUrl("https://www.googleapis.com/oauth2/v1/userinfo").build()
        .get()
        .uri {
            it.queryParam("access_token",accessToken)
                .queryParam("alt",alt)
                .build()
        }
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError) {
            it.bodyToMono(String::class.java)
                .map { body -> DynamicForbiddenException(body) }
        }
        .bodyToMono(object : ParameterizedTypeReference<GoogleEmailResponse>() {})
        .onErrorMap { throw it }
        .awaitFirstOrNull() ?: throw InternalServerError
}