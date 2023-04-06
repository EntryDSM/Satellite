package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.student.exception.StudentNotFoundException
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentInfoUseCase(
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort,
    private val filePort: FilePort
) {
    fun execute(studentId: UUID): DocumentInfoResponse {

        val student = studentPort.queryById(studentId) ?: throw StudentNotFoundException
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackList = feedbackPort.queryByDocumentId(document.id)
        val feedbackMap = feedbackList.associate { it.elementId to it.comment }

        val fileBaseUrl = filePort.getFileBaseUrl()
        return DocumentInfoResponse(fileBaseUrl, document, feedbackMap)
    }
}