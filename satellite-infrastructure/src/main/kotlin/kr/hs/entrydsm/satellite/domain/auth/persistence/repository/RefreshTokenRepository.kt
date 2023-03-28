package kr.hs.entrydsm.satellite.domain.auth.persistence.repository

import kr.hs.entrydsm.satellite.domain.auth.persistence.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {
    fun findByToken(token: String): RefreshToken?
}