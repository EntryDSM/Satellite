package kr.hs.entrydsm.satellite.domain.auth.usecase

import kr.hs.entrydsm.satellite.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.exception.RefreshTokenNotFoundException
import kr.hs.entrydsm.satellite.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.common.security.jwt.JwtGenerator

@UseCase
class ReissueTokenUseCase(
    private val jwtGenerator: JwtGenerator,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun execute(token: String): TokenResponse {
        val refreshToken = refreshTokenRepository.findByToken(token) ?: throw RefreshTokenNotFoundException
        return jwtGenerator.generateBothToken(refreshToken.id, refreshToken.authority)
    }
}