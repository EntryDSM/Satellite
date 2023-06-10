package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentInfoUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    suspend fun execute(studentId: UUID): DocumentInfoResponse {

        val document = documentPort.queryByWriterStudentId(studentId) ?: throw DocumentNotFoundException

        val feedbackMap = feedbackPort.queryByDocumentId(document.id)
            .associate { it.elementId to it.comment }

        return DocumentInfoResponse(document, feedbackMap)
    }
}