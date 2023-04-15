package kr.hs.entrydsm.satellite.global.thirdparty.aws.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws")
class AwsProperties(
    val region: String,
    val credentials: CredentialsProperties
) {
    class CredentialsProperties(
        val accessKey: String,
        val secretKey: String
    )
}