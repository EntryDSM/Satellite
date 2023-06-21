package kr.hs.entrydsm.satellite.global.logging

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "log.stash")
data class LogProperties(
    val name: String,
    val path: String,
    val size: Int
)