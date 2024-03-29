package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws.s3")
class AwsS3Properties(
    val url: String,
    val bucket: String,
    val profileFolder: String,
    val documentFolder: String,
    val pdfFolder: String
)