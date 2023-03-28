package kr.hs.entrydsm.satellite.domain.document.usecase

import java.util.UUID
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.feedback.persistence.repository.FeedbackRepository
import org.springframework.data.repository.findByIdOrNull

@UseCase
class ShareDocumentUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {
    fun execute(documentId: UUID) {

        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if (document.status != Status.SUBMITTED) {
            throw DocumentIllegalStatusException
        }

        val feedbackList = feedbackRepository.findByDocumentId(document.id)
        feedbackRepository.deleteAll(feedbackList)

        documentRepository.save(
            document.updateStatus(Status.SHARED)
        )
    }
}