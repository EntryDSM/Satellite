package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import kr.hs.entrydsm.satellite.domain.student.exception.StudentNotFoundException
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentInfoUseCase(
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    suspend fun execute(studentId: UUID): DocumentInfoResponse {

        val student = studentPort.queryById(studentId) ?: throw StudentNotFoundException
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackMap = feedbackPort.queryByDocumentId(document.id)
            .associate { it.elementId to it.comment }

        return DocumentInfoResponse(document, feedbackMap)
    }
}