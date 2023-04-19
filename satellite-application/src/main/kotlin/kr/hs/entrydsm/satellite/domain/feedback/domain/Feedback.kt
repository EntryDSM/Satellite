package kr.hs.entrydsm.satellite.domain.feedback.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*


interface Feedback {
    val documentId: UUID
    val elementId: UUID
    var comment: String
    val isApply: Boolean

    fun updateComment(comment: String) {
        this.comment = comment
    }
}

data class FeedbackDomain(
    override val documentId: UUID,
    override val elementId: UUID,
    override var comment: String,
    override val isApply: Boolean
) : Feedback, Domain