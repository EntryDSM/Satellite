package kr.hs.entrydsm.satellite.domain.document.spi

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import java.util.*

interface DocumentPort {

    suspend fun save(document: Document): Document
    suspend fun saveAll(documents: List<Document>)
    suspend fun queryById(documentId: UUID): Document?
    suspend fun queryByWriterStudentId(studentId: UUID): Document?
    suspend fun queryByYearAndWriterGrade(year: Int, writerGrade: Int): List<Document>
    suspend fun existByWriterStudentId(studentId: UUID): Boolean

    suspend fun queryByWriterInfoAndStatus(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?,
        status: DocumentStatus? = null,
    ): List<Document>
}