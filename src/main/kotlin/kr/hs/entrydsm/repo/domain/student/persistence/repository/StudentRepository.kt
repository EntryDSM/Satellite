package kr.hs.entrydsm.repo.domain.student.persistence.repository

import kr.hs.entrydsm.repo.domain.student.persistence.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StudentRepository: CrudRepository<Student, UUID> {

    fun findByEmail(email: String): Student?

    fun existsByEmail(email: String): Boolean

}