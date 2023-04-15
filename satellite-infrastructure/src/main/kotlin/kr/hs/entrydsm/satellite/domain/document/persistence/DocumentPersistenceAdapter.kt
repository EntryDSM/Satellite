package kr.hs.entrydsm.satellite.domain.document.persistence

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
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
    private val mongoTemplate: ReactiveMongoTemplate
) : DocumentPort {

    override suspend fun save(document: Document) =
        documentRepository.save(DocumentEntity.of(document)).awaitSingle()

    override suspend fun saveAll(documents: List<Document>) {
        documentRepository.saveAll(documents.map(DocumentEntity::of)).awaitFirstOrNull()
    }

    override suspend fun queryById(documentId: UUID) =
        documentRepository.findById(documentId).awaitSingleOrNull()

    override suspend fun queryByWriterStudentId(studentId: UUID) =
        documentRepository.findByWriterStudentId(studentId).awaitSingleOrNull()

    override suspend fun queryByYearAndWriterGrade(year: Int, writerGrade: Int): List<Document> =
        documentRepository.findByYearAndWriterGrade(year, writerGrade).collectList().awaitSingle()

    override suspend fun existByWriterStudentId(studentId: UUID) =
        documentRepository.existsByWriterStudentId(studentId).awaitSingle()

    override suspend fun queryByWriterInfoAndStatus(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?,
        status: DocumentStatus?
    ): List<Document> {

        var criteria = Criteria.where("writer.name").regex("${name}%")

        status?.let { criteria = criteria.and("status").`is`(it) }
        grade?.let { criteria = criteria.and("writer.grade").`is`(it) }
        classNum?.let { criteria = criteria.and("writer.classNum").`is`(it) }
        majorId?.let { criteria = criteria.and("writer.majorId").`is`(it) }

        return mongoTemplate.find(
            Query(criteria),
            DocumentEntity::class.java
        ).collectList().awaitSingle().map { it as Document }
    }
}