package kr.hs.entrydsm.satellite.domain.auth.domain

import java.util.UUID

data class RefreshToken(
    val id: UUID,
    val token: String,
    val authority: Authority,
    val timeToLive: Long
)