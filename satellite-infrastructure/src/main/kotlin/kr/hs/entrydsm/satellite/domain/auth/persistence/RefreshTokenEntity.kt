package kr.hs.entrydsm.satellite.domain.auth.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash
data class RefreshTokenEntity(

    @get:JvmName("getIdentifier")
    override var id: UUID,

    override val token: String,

    override val authority: Authority,

    @field:TimeToLive
    override val timeToLive: Long

) : RefreshToken, BaseUUIDEntity(id)