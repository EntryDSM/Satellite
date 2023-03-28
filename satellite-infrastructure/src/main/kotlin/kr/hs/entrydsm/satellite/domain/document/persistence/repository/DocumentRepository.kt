package kr.hs.entrydsm.satellite.domain.document.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface DocumentRepository : MongoRepository<Document, UUID>, QuerydslPredicateExecutor<Document> {

    fun findByWriterStudentId(studentId: UUID): Document?

    fun findByYearAndWriterGrade(year: Int, studentGrade: String): List<Document>

    fun findTopByStatusAndWriterStudentNumberIsGreaterThanOrderByWriterStudentNumber(
        status: Status,
        studentNumber: Int
    ): Document?

    fun findTopByStatusAndWriterStudentNumberIsLessThanOrderByWriterStudentNumberDesc(
        status: Status,
        studentNumber: Int
    ): Document?

    fun deleteByYear(year: Int)
}