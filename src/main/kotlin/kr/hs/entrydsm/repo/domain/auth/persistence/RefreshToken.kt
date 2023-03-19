package kr.hs.entrydsm.repo.domain.auth.persistence

import java.util.UUID
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class RefreshToken(

    @field:Id
    val userId: UUID,

    @field:NotBlank
    val token: String,

    @field:TimeToLive
    val timeToLive: Long

)