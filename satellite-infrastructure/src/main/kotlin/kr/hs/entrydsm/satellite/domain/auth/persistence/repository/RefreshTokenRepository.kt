package kr.hs.entrydsm.satellite.domain.auth.persistence.repository

import kr.hs.entrydsm.satellite.domain.auth.persistence.RefreshTokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshTokenEntity, UUID> {
    fun findByToken(token: String): RefreshTokenEntity?
}