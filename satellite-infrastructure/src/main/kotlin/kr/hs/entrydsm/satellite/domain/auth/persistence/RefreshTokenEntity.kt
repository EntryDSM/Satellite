package kr.hs.entrydsm.satellite.domain.auth.persistence

import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*
import javax.validation.constraints.NotBlank

@RedisHash
class RefreshTokenEntity(

    @field:Id
    override val id: UUID,

    @field:NotBlank
    override val token: String,

    override val authority: Authority,

    @field:TimeToLive
    override val timeToLive: Long

) : RefreshToken(id, token, authority, timeToLive)