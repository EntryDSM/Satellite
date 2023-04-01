package kr.hs.entrydsm.satellite.domain.document.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface DocumentRepository : MongoRepository<DocumentJpaEntity, UUID>, QuerydslPredicateExecutor<DocumentJpaEntity> {

    fun queryByWriterStudentId(studentId: UUID): DocumentJpaEntity?

    fun findByYearAndWriterGrade(year: Int, studentGrade: String): List<DocumentJpaEntity>

    fun findTopByStatusAndWriterStudentNumberIsGreaterThanOrderByWriterStudentNumber(
        documentStatus: DocumentStatus,
        studentNumber: Int
    ): DocumentJpaEntity?

    fun findTopByStatusAndWriterStudentNumberIsLessThanOrderByWriterStudentNumberDesc(
        documentStatus: DocumentStatus,
        studentNumber: Int
    ): DocumentJpaEntity?

    fun deleteByYear(year: Int)
}