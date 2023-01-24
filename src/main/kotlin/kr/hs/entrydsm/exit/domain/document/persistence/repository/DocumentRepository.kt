package kr.hs.entrydsm.exit.domain.document.persistence.repository


import kr.hs.entrydsm.exit.domain.document.persistence.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.util.*


interface DocumentRepository: MongoRepository<Document, UUID>, QuerydslPredicateExecutor<Document> {

    fun findByWriterStudentId(studentId: UUID): Document?

    fun findByIdAndWriterStudentId(id: UUID, studentId: UUID): Document?
}