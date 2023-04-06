package kr.hs.entrydsm.satellite.domain.auth.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken
import kr.hs.entrydsm.satellite.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.satellite.domain.auth.spi.RefreshTokenPort

@Adapter
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenPort {

    override fun save(refreshToken: RefreshToken): RefreshTokenEntity =
        refreshTokenRepository.save(RefreshTokenEntity.of(refreshToken))

    override fun queryByToken(token: String) =
        refreshTokenRepository.findByToken(token)
}