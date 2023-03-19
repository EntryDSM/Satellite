package kr.hs.entrydsm.repo.domain.auth.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import javax.validation.constraints.NotBlank

@RedisHash
class VerificationCode(

    @field:Id
    val id: String,

    @field:NotBlank
    @field:Indexed
    val code: String,

    val isVerified: Boolean,

    val countOfSend: Int,

    @field:TimeToLive
    val timeToLive: Long

) {
    fun checkAndIncreaseCountOfSend(limitCountOfSend: Int): Int {
        if (countOfSend >= limitCountOfSend) {
            throw kr.hs.entrydsm.repo.domain.auth.exception.TooManySendVerificationException
        }
        return countOfSend + 1
    }
}