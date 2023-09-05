package kr.hs.entrydsm.satellite.domain.document.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.*

@Adapter
class DocumentPersistenceAdapter(
    private val documentRepository: DocumentRepository,
    private val mongoTemplate: ReactiveMongoTemplate,
    private val objectMapper: ObjectMapper
) : DocumentPort {

    override suspend fun save(document: Document): DocumentEntity =
        documentRepository.save(objectMapper.convertValue(document)).awaitFirst()

    override suspend fun saveAll(documents: List<Document>) {
        documentRepository.saveAll(documents.map(objectMapper::convertValue)).awaitFirstOrNull()
    }

    override suspend fun queryById(documentId: UUID) =
        documentRepository.findById(documentId).awaitSingleOrNull()

    override suspend fun queryByWriterStudentId(studentId: UUID) =
        documentRepository.findByWriterStudentId(studentId).awaitSingleOrNull()

    override suspend fun queryByYearAndWriterGrade(year: Int, writerGrade: Int): List<Document> =
        documentRepository.findByYearAndWriterGrade(year, writerGrade).collectList().awaitFirst()

    override suspend fun existByWriterStudentId(studentId: UUID) =
        documentRepository.existsByWriterStudentId(studentId).awaitFirst()

    override suspend fun queryByWriterInfoAndStatus(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?,
        status: DocumentStatus?
    ): List<Document> {

        var criteria = Criteria.where("writer.name").regex("^" + (name ?: ""))

        with(criteria) {
            status?.let { criteria = and("status").`is`(it) }
            grade?.let { criteria = and("writer.grade").`is`(it) }
            classNum?.let { criteria = and("writer.classNum").`is`(it) }
            majorId?.let { criteria = and("writer.majorId").`is`(it) }
        }

        return mongoTemplate.find(
            Query(criteria),
            DocumentEntity::class.java
        ).collectList().awaitFirst().map { it as Document }
    }
}