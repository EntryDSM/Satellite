package kr.hs.entrydsm.satellite.domain.auth.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

class RefreshToken(
    val id: UUID,
    val token: String,
    val authority: Authority,
    val timeToLive: Long
) : Domain