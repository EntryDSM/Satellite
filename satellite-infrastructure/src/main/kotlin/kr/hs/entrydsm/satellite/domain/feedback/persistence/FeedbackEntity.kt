package kr.hs.entrydsm.satellite.domain.feedback.persistence

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.util.*

@Table(name = "tbl_feedback")
data class FeedbackEntity(
    override val documentId: UUID,
    override val elementId: UUID,
    override var comment: String,
    override val isApply: Boolean
) : Feedback

data class FeedbackId(
    val elementId: UUID,
    val documentId: UUID
) : Serializable