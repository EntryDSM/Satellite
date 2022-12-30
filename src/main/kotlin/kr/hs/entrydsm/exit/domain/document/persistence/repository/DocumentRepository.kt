package kr.hs.entrydsm.exit.domain.document.persistence.repository


import kr.hs.entrydsm.exit.domain.document.persistence.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface DocumentRepository: MongoRepository<Document, UUID> {
    fun findByWriterStudentId(studentId: UUID): Document?
    fun findByIdAndWriterStudentId(id: UUID, studentId: UUID): Document?
}