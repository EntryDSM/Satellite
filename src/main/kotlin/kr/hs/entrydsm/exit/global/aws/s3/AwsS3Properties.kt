package kr.hs.entrydsm.exit.global.aws.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws.s3")
data class AwsS3Properties (
    val url: String,
    val bucket: String,
    val profileFolder: String,
    val documentFolder: String
)
