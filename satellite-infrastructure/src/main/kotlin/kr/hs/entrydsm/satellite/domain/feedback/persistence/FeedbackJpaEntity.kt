package kr.hs.entrydsm.satellite.domain.feedback.persistence

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@IdClass(FeedbackId::class)
@Entity
@Table(name = "tbl_feedback")
class FeedbackJpaEntity(

    @Id
    @Column(nullable = false)
    override val documentId: UUID,

    @Id
    @Column(nullable = false)
    override val elementId: UUID,

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    override val comment: String,

    @Column(columnDefinition = "BIT(1)", nullable = false)
    override val isApply: Boolean

) : Feedback(documentId, elementId, comment, isApply)

@Embeddable
data class FeedbackId(

    @Column(nullable = false)
    val elementId: UUID,

    @Column(nullable = false)
    val documentId: UUID

) : Serializable