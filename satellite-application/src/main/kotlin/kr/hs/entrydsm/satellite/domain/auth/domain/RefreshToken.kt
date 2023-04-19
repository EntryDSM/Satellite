package kr.hs.entrydsm.satellite.domain.auth.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface RefreshToken {
    val id: UUID
    val token: String
    val authority: Authority
    val timeToLive: Long
}

data class RefreshTokenDomain(
    override val id: UUID,
    override val token: String,
    override val authority: Authority,
    override val timeToLive: Long
) : RefreshToken, Domain