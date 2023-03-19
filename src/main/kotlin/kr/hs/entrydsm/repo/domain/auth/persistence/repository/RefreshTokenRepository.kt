package kr.hs.entrydsm.repo.domain.auth.persistence.repository

import kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RefreshTokenRepository : CrudRepository<kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken, UUID>