package kr.hs.entrydsm.repo.domain.auth.persistence.repository

import java.util.UUID
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken, UUID>