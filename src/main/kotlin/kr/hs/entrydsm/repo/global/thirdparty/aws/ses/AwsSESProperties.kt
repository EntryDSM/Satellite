package kr.hs.entrydsm.repo.global.thirdparty.aws.ses

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws.ses")
class AwsSESProperties(
    val accessKey: String,
    val secretKey: String,
    val region: String
)