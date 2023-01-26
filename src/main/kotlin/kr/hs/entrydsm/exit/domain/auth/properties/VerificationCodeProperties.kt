package kr.hs.entrydsm.exit.domain.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "domain.auth.code")
class VerificationCodeProperties(
    val limitCountOfSend: Int,
    val timeToLive: Long
)