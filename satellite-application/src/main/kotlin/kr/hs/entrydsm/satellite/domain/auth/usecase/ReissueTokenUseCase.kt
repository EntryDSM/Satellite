package kr.hs.entrydsm.satellite.domain.auth.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.exception.RefreshTokenNotFoundException
import kr.hs.entrydsm.satellite.domain.auth.spi.RefreshTokenPort
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort

@UseCase
class ReissueTokenUseCase(
    private val tokenPort: TokenPort,
    private val refreshTokenPort: RefreshTokenPort
) {
    suspend fun execute(token: String): TokenResponse {
        val refreshToken = refreshTokenPort.queryByToken(token) ?: throw RefreshTokenNotFoundException
        return tokenPort.generateBothToken(refreshToken.id, refreshToken.authority)
    }
}