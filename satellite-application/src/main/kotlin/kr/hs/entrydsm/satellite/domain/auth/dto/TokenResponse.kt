package kr.hs.entrydsm.satellite.domain.auth.dto

import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val accessExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshExpiredAt: LocalDateTime,
)