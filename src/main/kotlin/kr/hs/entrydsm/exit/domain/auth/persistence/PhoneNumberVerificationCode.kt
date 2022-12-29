package kr.hs.entrydsm.exit.domain.auth.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RedisHash
class PhoneNumberVerificationCode(

    @field:Id
    val phoneNumber: String,

    @field:NotBlank
    @field:Indexed
    val code: String,

    @field:NotNull
    val isVerified: Boolean,

    @field:NotNull
    val countOfSend: Int,

    @field:TimeToLive
    val timeToLive: Long

)