package kr.hs.entrydsm.repo.domain.feedback.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.feedback.persistence.FeedbackId
import kr.hs.entrydsm.repo.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request.DeleteFeedbackRequest
import org.springframework.data.repository.findByIdOrNull

@UseCase
class DeleteFeedbackUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {
    fun execute(request: DeleteFeedbackRequest) {

        val document = documentRepository.findByIdOrNull(request.documentId) ?: throw DocumentNotFoundException

        feedbackRepository.deleteById(
            FeedbackId(
                documentId = document.id,
                elementId = request.elementId
            )
        )
    }
}