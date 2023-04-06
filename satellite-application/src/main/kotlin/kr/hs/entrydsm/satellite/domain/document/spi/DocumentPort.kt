package kr.hs.entrydsm.satellite.domain.document.spi

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import java.util.*

interface DocumentPort {

    fun save(document: Document): Document
    fun saveAll(documents: List<Document>): List<Document>
    fun queryById(documentId: UUID): Document?
    fun queryByWriterStudentId(studentId: UUID): Document?
    fun queryByYearAndWriterGrade(year: Int, writerGrade: Int): List<Document>
    fun existByWriterStudentId(studentId: UUID): Boolean

    fun queryByStatusAndWriterInfo(
        status: DocumentStatus,
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?
    ): List<Document>

    fun queryByWriterInfo(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?
    ): List<Document>

}