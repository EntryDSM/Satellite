package com.example.exit.domain.auth.persistence.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "verification.phone")
class PhoneNumberVerificationCodeProperties(
    var limitCountOfSend: Int,
    val baseTimeToLive: Long,
    val codeLength: Int,
    val key: String,
    val secret: String,
    val phoneNumber: String
)