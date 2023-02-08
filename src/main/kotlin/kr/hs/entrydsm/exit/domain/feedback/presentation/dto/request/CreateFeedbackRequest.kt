package kr.hs.entrydsm.exit.domain.feedback.presentation.dto.request

import org.hibernate.validator.constraints.Length
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateFeedbackRequest(

    
    val documentId: UUID,

    
    val elementId: UUID,

    @field:NotBlank
    @field:Length(max = 1000)
    val comment: String
)
