package kr.hs.entrydsm.satellite.domain.teacher.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.teacher.persistence.Teacher
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : CrudRepository<Teacher, UUID> {

    fun findByAccountId(accountId: String): Teacher?
}