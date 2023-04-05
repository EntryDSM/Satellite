package kr.hs.entrydsm.satellite.domain.document.persistence.repository

import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.util.*

interface DocumentRepository : MongoRepository<DocumentJpaEntity, UUID>, QuerydslPredicateExecutor<DocumentJpaEntity> {

    fun queryByWriterStudentId(studentId: UUID): DocumentJpaEntity?

    fun findByYearAndWriterGrade(year: Int, studentGrade: String): List<DocumentJpaEntity>

    fun existsByWriterStudentId(studentId: UUID): Boolean

}