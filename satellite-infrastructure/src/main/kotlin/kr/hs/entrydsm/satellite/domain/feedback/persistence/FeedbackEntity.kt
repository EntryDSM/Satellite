package kr.hs.entrydsm.satellite.domain.feedback.persistence

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.util.*

@Table(name = "tbl_feedback")
class FeedbackEntity(
    override val documentId: UUID,
    override val elementId: UUID,
    override val comment: String,
    override val isApply: Boolean
) : Feedback(documentId, elementId, comment, isApply) {
    companion object {
        fun of(feedback: Feedback) = feedback.run {
            FeedbackEntity(
                documentId = documentId,
                elementId = elementId,
                comment = comment,
                isApply = isApply
            )
        }
    }
}

data class FeedbackId(
    val elementId: UUID,
    val documentId: UUID
) : Serializable