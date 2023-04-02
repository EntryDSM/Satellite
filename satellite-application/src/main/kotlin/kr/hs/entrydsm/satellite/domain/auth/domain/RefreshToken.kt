package kr.hs.entrydsm.satellite.domain.auth.domain

import java.util.UUID

open class RefreshToken(
    open val id: UUID,
    open val token: String,
    open val authority: Authority,
    open val timeToLive: Long
)