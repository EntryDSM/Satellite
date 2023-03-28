package kr.hs.entrydsm.satellite.domain.feedback.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.response.FeedbackListResponse
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

@ReadOnlyUseCase
class QueryMyDocumentFeedbackUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {
    fun execute(): FeedbackListResponse {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackList = feedbackRepository.findByDocumentId(document.id)
        val feedbackNameMap = document.getElementNameMap()

        return FeedbackListResponse(
            documentId = document.id,
            feedbackList = feedbackList.map {
                FeedbackListResponse.FeedbackResponse(
                    elementId = it.elementId,
                    elementName = feedbackNameMap[it.elementId]!!,
                    comment = it.comment,
                )
            }
        )
    }
}