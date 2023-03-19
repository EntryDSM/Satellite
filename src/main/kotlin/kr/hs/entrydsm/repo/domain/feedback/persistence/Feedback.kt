package kr.hs.entrydsm.repo.domain.feedback.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@IdClass(FeedbackId::class)
@Entity
@Table(name = "tbl_feedback")
class Feedback(

    @Id
    @Column(nullable = false)
    val documentId: UUID,

    @Id
    @Column(nullable = false)
    val elementId: UUID,

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    val comment: String,

    @Column(columnDefinition = "BIT(1)", nullable = false)
    val isApply: Boolean

) {

    fun applyFeedback() = copy(isApply = true)

    fun updateComment(comment: String) = copy(comment = comment)

    private fun copy(
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