package kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request

import java.util.UUID
import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class UpdateFeedbackWebRequest(

    val documentId: UUID,

    val elementId: UUID,

    @field:NotBlank
    @field:Length(max = 1000)
    val comment: String
)