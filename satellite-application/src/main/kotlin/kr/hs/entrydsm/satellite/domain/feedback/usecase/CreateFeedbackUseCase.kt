package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.feedback.exception.FeedbackAlreadyExistException
import kr.hs.entrydsm.satellite.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.persistence.FeedbackId
import kr.hs.entrydsm.satellite.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.CreateFeedbackRequest
import org.springframework.data.repository.findByIdOrNull

@UseCase
class CreateFeedbackUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {
    fun execute(request: CreateFeedbackRequest) {

        val document = documentRepository.findByIdOrNull(request.documentId) ?: throw DocumentNotFoundException

        checkFeedbackIsNotExist(request)

        if (document.status != Status.SUBMITTED) {
            throw DocumentIllegalStatusException
        }

        feedbackRepository.save(
            Feedback(
                documentId = document.id,
                elementId = request.elementId,
                comment = request.comment,
                isApply = false
            )
        )
    }

    private fun checkFeedbackIsNotExist(request: CreateFeedbackRequest) {

        val feedbackId = FeedbackId(
            documentId = request.documentId,
            elementId = request.elementId
        )

        if (feedbackRepository.existsById(feedbackId)) {
            throw FeedbackAlreadyExistException
        }
    }
}