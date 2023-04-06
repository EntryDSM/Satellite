package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@UseCase
class ShareDocumentUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    fun execute(documentId: UUID) {

        val document = documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        if (document.status != DocumentStatus.SUBMITTED) {
            throw DocumentIllegalStatusException
        }

        val feedbackList = feedbackPort.queryByDocumentId(document.id)
        feedbackPort.deleteAll(feedbackList)

        documentPort.save(
            document.changeStatus(DocumentStatus.SHARED)
        )
    }
}