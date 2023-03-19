package kr.hs.entrydsm.exit.domain.document.persistence.repository


import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.util.UUID


interface DocumentRepository: MongoRepository<Document, UUID>, QuerydslPredicateExecutor<Document> {

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
