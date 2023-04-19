package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@UseCase
class DeleteFeedbackUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    suspend fun execute(documentId: UUID, elementId: UUID) {

        documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        feedbackPort.deleteByDocumentIdAndFeedbackId(documentId, elementId)
    }
}