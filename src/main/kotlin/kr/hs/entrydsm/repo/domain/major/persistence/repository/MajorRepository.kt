package kr.hs.entrydsm.repo.domain.major.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.major.persistence.Major
import org.springframework.data.repository.CrudRepository

interface MajorRepository : CrudRepository<Major, UUID> {
    fun findByNameContaining(name: String): List<Major>
}