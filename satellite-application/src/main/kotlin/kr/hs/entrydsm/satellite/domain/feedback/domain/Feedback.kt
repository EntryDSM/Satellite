package kr.hs.entrydsm.satellite.domain.feedback.domain

import java.util.*

data class Feedback(

    val documentId: UUID,

    val elementId: UUID,

    val comment: String,

    val isApply: Boolean
) {

    fun applyFeedback() = copy(isApply = true)

    fun updateComment(comment: String) = copy(comment = comment)
}