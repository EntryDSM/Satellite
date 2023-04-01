package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.exception.FeedbackAlreadyExistException
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@UseCase
class CreateFeedbackUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    fun execute(
        documentId: UUID,
        elementId: UUID,
        comment: String
    ) {
        val document = documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        if (feedbackPort.existsByDocumentIdAndFeedbackId(documentId, elementId)) {
            throw FeedbackAlreadyExistException
        }

        if (document.status != DocumentStatus.SUBMITTED) {
            throw DocumentIllegalStatusException
        }

        feedbackPort.save(
            Feedback(
                documentId = documentId,
                elementId = elementId,
                comment = comment,
                isApply = false
            )
        )
    }
}