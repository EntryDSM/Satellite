package kr.hs.entrydsm.satellite.domain.auth.persistence

import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*
import javax.validation.constraints.NotBlank

@RedisHash
class RefreshToken(

    @field:Id
    val id: UUID,

    @field:NotBlank
    val token: String,

    val authority: Authority,

    @field:TimeToLive
    val timeToLive: Long

)