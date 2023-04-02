package kr.hs.entrydsm.satellite.domain.feedback.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.UUID

open class Feedback(
    open val documentId: UUID,
    open val elementId: UUID,
    open val comment: String,
    open val isApply: Boolean
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