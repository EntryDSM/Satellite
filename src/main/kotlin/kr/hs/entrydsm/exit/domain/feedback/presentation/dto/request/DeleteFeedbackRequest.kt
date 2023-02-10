package kr.hs.entrydsm.exit.domain.feedback.presentation.dto.request

import java.util.*
import javax.validation.constraints.NotNull

data class DeleteFeedbackRequest(

    
    val documentId: UUID,

    
    val elementId: UUID,
)