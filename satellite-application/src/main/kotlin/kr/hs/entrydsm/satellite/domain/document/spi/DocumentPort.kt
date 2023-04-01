package kr.hs.entrydsm.satellite.domain.document.spi

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import java.util.*

interface DocumentPort {

    fun save(document: Document): Document
    fun queryById(documentId: UUID): Document?
    fun queryByWriterStudentId(studentId: UUID): Document?
    fun existByWriterStudentId(id: UUID): Boolean

    fun queryByStatusAndWriterInfo(
        documentStatus: DocumentStatus?,
        name: String?,
        grade: String?,
        classNum: String?,
        majorId: UUID?
    ): List<Document>

    fun queryByWriterInfo(
        name: String?,
        grade: String?,
        classNum: String?,
        majorId: UUID?
    ): List<Document>
}