package kr.hs.entrydsm.repo.global.thirdparty.aws.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws.s3")
data class AwsS3Properties(
    val bucket: String,
    val profileFolder: String,
    val documentFolder: String,
    val pdfFolder: String
)