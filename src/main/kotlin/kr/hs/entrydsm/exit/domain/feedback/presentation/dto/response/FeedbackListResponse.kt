package kr.hs.entrydsm.exit.domain.feedback.presentation.dto.response

import java.util.*

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