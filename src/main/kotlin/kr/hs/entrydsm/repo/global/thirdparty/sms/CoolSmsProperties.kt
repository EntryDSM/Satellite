package kr.hs.entrydsm.repo.global.thirdparty.sms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cool-sms")
class CoolSmsProperties(
    val key: String,
    val secret: String,
    val phoneNumber: String
)