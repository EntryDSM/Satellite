package kr.hs.entrydsm.satellite.domain.student.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.student.persistence.StudentJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : CrudRepository<StudentJpaEntity, UUID> {

    fun findByEmail(email: String): StudentJpaEntity?

    fun existsByEmail(email: String): Boolean
}