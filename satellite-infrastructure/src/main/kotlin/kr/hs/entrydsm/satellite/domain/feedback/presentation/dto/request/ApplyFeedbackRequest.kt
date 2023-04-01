package kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request

import java.util.UUID
import javax.validation.constraints.NotNull

data class ApplyFeedbackRequest(

    @field:NotNull
    val documentId: UUID?,

    @field:NotNull
    val elementId: UUID?
)