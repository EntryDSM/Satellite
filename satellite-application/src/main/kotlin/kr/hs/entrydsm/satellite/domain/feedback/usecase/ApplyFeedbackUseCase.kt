package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.feedback.exception.FeedbackNotFoundException
import kr.hs.entrydsm.satellite.domain.feedback.persistence.FeedbackId
import kr.hs.entrydsm.satellite.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.ApplyFeedbackRequest
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