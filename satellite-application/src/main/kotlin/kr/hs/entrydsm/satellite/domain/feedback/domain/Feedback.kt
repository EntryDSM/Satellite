package kr.hs.entrydsm.satellite.domain.feedback.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

data class Feedback(
    val documentId: UUID,
    val elementId: UUID,
    val comment: String,
    val isApply: Boolean
) : Domain {
    fun updateComment(comment: String) = copy(comment = comment)

    protected constructor() : this(UUID(0,0), UUID(0,0), "", false)
}