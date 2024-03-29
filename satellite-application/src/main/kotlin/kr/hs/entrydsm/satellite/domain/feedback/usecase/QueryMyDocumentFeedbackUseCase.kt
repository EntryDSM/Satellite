package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.dto.FeedbackListResponse
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort

@ReadOnlyUseCase
class QueryMyDocumentFeedbackUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    suspend fun execute(): FeedbackListResponse {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackList = feedbackPort.queryByDocumentId(document.id)
        val feedbackNameMap = document.getElementNameMap()

        return FeedbackListResponse(
            documentId = document.id,
            feedbackList = feedbackList.mapNotNull { feedback ->
                feedbackNameMap[feedback.elementId]?.let {
                    FeedbackListResponse.FeedbackResponse(
                        elementId = feedback.elementId,
                        elementName = it,
                        comment = feedback.comment,
                    )
                }
            }
        )
    }
}