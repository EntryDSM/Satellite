package kr.hs.entrydsm.exit.domain.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties(prefix = "auth")
@ConstructorBinding
data class GoogleOauthProperties(
    val clientId: String,
    val secretKey: String,
    val baseUrl: String,
    val redirectUrl: String
)
