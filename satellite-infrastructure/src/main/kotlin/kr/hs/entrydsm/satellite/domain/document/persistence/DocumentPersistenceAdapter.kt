package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

@Adapter
class DocumentPersistenceAdapter(
    private val documentRepository: DocumentRepository
) : DocumentPort {

    override fun save(document: Document) =
        documentRepository.save(document as DocumentJpaEntity)

    override fun saveAll(documents: List<Document>) =
        documentRepository.saveAll(documents.map { it as DocumentJpaEntity })

    override fun queryById(documentId: UUID) =
        documentRepository.findByIdOrNull(documentId)

    override fun queryByWriterStudentId(studentId: UUID) =
        documentRepository.queryByWriterStudentId(studentId)

    override fun queryByYearAndWriterGrade(year: Int, writerGrade: Int) =
        documentRepository.findByYearAndWriterGrade(year, writerGrade.toString())

    override fun existByWriterStudentId(studentId: UUID) =
        documentRepository.existsByWriterStudentId(studentId)

    override fun queryByStatusAndWriterInfo(
        documentStatus: DocumentStatus?,
        name: String?,
        grade: String?,
        classNum: String?,
        majorId: UUID?
    ) = documentRepository.

    override fun queryByWriterInfo(name: String?, grade: String?, classNum: String?, majorId: UUID?): List<Document> {
        return documentRepository.findBy(
            document.writer.name.contains(name),
            eqGrade(grade),
            eqClassNum(classNum),
            eqMajorId(majorId)
        )
    }

    private fun eqGrade(grade: String?): BooleanExpression? {
        return if (grade != null) document.writer.grade.eq(grade) else null
    }

    private fun eqClassNum(classNum: String?): BooleanExpression? {
        return if (classNum != null) document.writer.classNum.eq(classNum) else null
    }

    private fun eqMajorId(majorId: UUID?): BooleanExpression? {
        return if (majorId != null) document.writer.majorId.eq(majorId) else null
    }
}