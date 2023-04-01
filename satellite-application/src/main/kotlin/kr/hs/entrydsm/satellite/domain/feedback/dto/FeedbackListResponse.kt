package kr.hs.entrydsm.satellite.domain.feedback.dto

import java.util.UUID

data class FeedbackListResponse(
    val documentId: UUID,
    val feedbackList: List<FeedbackResponse>
) {
    data class FeedbackResponse(
        val elementId: UUID,
        val elementName: String,
        val comment: String,
    )
}