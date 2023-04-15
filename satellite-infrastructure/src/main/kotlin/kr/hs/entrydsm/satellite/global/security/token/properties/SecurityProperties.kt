package kr.hs.entrydsm.satellite.global.security.token.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.util.*

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class SecurityProperties(
    secretKey: String,
    val accessExp: Long,
    val refreshExp: Long
) {
    val secretKey: String = Base64.getEncoder().encodeToString(secretKey.toByteArray())
}