package kr.hs.entrydsm.satellite.domain.document.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.persistence.StudentRepository
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.*

@Adapter
class DocumentPersistenceAdapter(
    private val documentRepository: DocumentRepository,
    private val studentRepository: StudentRepository,
    private val mongoTemplate: ReactiveMongoTemplate,
    private val objectMapper: ObjectMapper
) : DocumentPort {

    override suspend fun save(document: Document): DocumentEntity =
        documentRepository.save(
            document.run {
                DocumentEntity(
                    id = id,
                    year = year,
                    isDeleted = isDeleted,
                    status = status,
                    writer = writer,
                    introduce = introduce,
                    skillSet = skillSet,
                    projectList = projectList,
                    awardList = awardList,
                    certificateList = certificateList,
                    activityList = activityList
                )
            }
        ).awaitSingle()

    override suspend fun saveAll(documents: List<Document>) {
        documentRepository.saveAll(documents.map(objectMapper::convertValue)).awaitFirstOrNull()
    }

    override suspend fun queryById(documentId: UUID) =
        documentRepository.findById(documentId).awaitFirstOrNull()

    override suspend fun queryByWriterStudentId(studentId: UUID): DocumentEntity =
        documentRepository.findByWriterStudentId(studentId).awaitFirst()

    override suspend fun queryByYearAndWriterGrade(year: Int, writerGrade: Int): List<Document> =
        documentRepository.findByYearAndWriterGrade(year, writerGrade).collectList().awaitFirst()

    override suspend fun existByWriterStudentId(studentId: UUID): Boolean =
        documentRepository.existsByWriterStudentId(studentId).awaitFirst()

    override suspend fun queryByWriterInfoAndStatus(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?,
        status: DocumentStatus?
    ): List<Document> {

        var criteria = Criteria.where(PropertyName.name).regex("^" + (name ?: ""))

        with(criteria) {
            status?.let { criteria = and(PropertyName.status).`is`(it) }
            grade?.let { criteria = and(PropertyName.grade).`is`(it) }
            classNum?.let { criteria = and(PropertyName.classNum).`is`(it) }
            majorId?.let { criteria = and(PropertyName.majorId).`is`(it) }
        }

        val sort = Sort.by(
            Sort.Direction.ASC,
            PropertyName.grade,
            PropertyName.classNum,
            PropertyName.number
        )

        return mongoTemplate.find(
            Query(criteria).with(sort),
            DocumentEntity::class.java
        ).collectList().awaitFirst().map { it as Document }
    }

    object PropertyName {
        const val name = "writer.name"
        const val status = "status"
        const val grade = "writer.grade"
        const val classNum = "writer.classNum"
        const val number = "writer.number"
        const val majorId = "writer.majorId"
    }

}