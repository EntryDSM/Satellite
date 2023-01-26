package kr.hs.entrydsm.exit.domain.feedback.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.feedback.exception.FeedbackNotFoundException
import kr.hs.entrydsm.exit.domain.feedback.persistence.FeedbackId
import kr.hs.entrydsm.exit.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.exit.domain.feedback.presentation.dto.request.ApplyFeedbackRequest
import org.springframework.data.repository.findByIdOrNull

@UseCase
class ApplyFeedbackUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {

    fun execute(request: ApplyFeedbackRequest) {

        val document = documentRepository.findByIdOrNull(request.documentId) ?: throw DocumentNotFoundException

        val feedback = feedbackRepository.findByIdOrNull(
            FeedbackId(
                documentId = document.id,
                elementId = request.elementId
            )
        ) ?: throw FeedbackNotFoundException

        feedbackRepository.delete(feedback)
    }
}