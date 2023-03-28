package kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request

import java.util.UUID

data class ApplyFeedbackRequest(

    val documentId: UUID,

    val elementId: UUID

)