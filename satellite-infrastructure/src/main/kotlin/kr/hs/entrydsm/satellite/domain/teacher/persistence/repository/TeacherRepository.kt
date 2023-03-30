package kr.hs.entrydsm.satellite.domain.teacher.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.teacher.persistence.TeacherJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : CrudRepository<TeacherJpaEntity, UUID> {

    fun findByAccountId(accountId: String): TeacherJpaEntity?
}