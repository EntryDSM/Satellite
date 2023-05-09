package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.domain.FeedbackDomain
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@UseCase
class CreateFeedbackUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    suspend fun execute(
        documentId: UUID,
        elementId: UUID,
        comment: String
    ) {
        val document = documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        if (document.status != DocumentStatus.SUBMITTED) {
            throw DocumentIllegalStatusException
        }

        feedbackPort.deleteByDocumentIdAndElementId(documentId, elementId)
        feedbackPort.save(
            FeedbackDomain(
                documentId = documentId,
                elementId = elementId,
                comment = comment,
                isApply = false
            )
        )
    }
}