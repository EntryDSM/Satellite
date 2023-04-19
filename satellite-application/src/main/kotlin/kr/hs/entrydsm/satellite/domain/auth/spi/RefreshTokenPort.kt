package kr.hs.entrydsm.satellite.domain.auth.spi

import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken

interface RefreshTokenPort {
    suspend fun save(refreshToken: RefreshToken): RefreshToken
    suspend fun queryByToken(token: String): RefreshToken?
}