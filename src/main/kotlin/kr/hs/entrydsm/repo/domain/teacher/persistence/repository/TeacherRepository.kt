package kr.hs.entrydsm.repo.domain.teacher.persistence.repository

import kr.hs.entrydsm.repo.domain.teacher.persistence.Teacher
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TeacherRepository : CrudRepository<Teacher, UUID> {

    fun findByAccountId(accountId: String): Teacher?

}