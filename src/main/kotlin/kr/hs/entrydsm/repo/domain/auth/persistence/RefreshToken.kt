package kr.hs.entrydsm.repo.domain.auth.persistence

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank

@RedisHash
class RefreshToken(

    @field:Id
    val userId: UUID,

    @field:NotBlank
    val token: String,

    @field:TimeToLive
    val timeToLive: Long

)