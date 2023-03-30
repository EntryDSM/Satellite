package kr.hs.entrydsm.satellite.domain.auth.spi

import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import java.util.*

interface TokenPort {
    fun generateBothToken(userId: UUID, auth: Authority): TokenResponse
}