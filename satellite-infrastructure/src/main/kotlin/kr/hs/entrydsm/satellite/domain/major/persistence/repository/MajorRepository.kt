package kr.hs.entrydsm.satellite.domain.major.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.major.persistence.MajorJpaEntity
import org.springframework.data.repository.CrudRepository

interface MajorRepository : CrudRepository<MajorJpaEntity, UUID> {
    fun findByNameContaining(name: String): List<MajorJpaEntity>
}