package kr.hs.entrydsm.repo.domain.major.persistence.repository

import kr.hs.entrydsm.repo.domain.major.persistence.Major
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface MajorRepository: CrudRepository<Major, UUID> {
    fun findByNameContaining(name: String): List<Major>
}