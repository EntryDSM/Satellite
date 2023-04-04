package kr.hs.entrydsm.satellite.domain.feedback.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

class Feedback(
    val documentId: UUID,
    val elementId: UUID,
    val comment: String,
    val isApply: Boolean
) : Domain {
    fun updateComment(comment: String) = copy(comment = comment)

    fun copy(
        documentId: UUID = this.documentId,
        elementId: UUID = this.elementId,
        comment: String = this.comment,
        isApply: Boolean = this.isApply
    ) = Feedback(
        documentId = documentId,
        elementId = elementId,
        comment = comment,
        isApply = isApply
    )
}