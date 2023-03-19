package kr.hs.entrydsm.repo.global.security.jwt.properties

import java.util.Base64
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
data class SecurityProperties(
    var secretKey: String,
    val accessExp: Long,
    val refreshExp: Long
) {
    init {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }
}