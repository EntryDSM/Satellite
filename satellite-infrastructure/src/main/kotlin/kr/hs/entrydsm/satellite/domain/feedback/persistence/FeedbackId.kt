package kr.hs.entrydsm.satellite.domain.feedback.persistence

import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class FeedbackId(

    @Column(nullable = false)
    val elementId: UUID,

    @Column(nullable = false)
    val documentId: UUID

) : Serializable