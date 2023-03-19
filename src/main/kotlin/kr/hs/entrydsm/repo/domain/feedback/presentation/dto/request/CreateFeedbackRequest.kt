package kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request

import java.util.UUID
import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class CreateFeedbackRequest(

    val documentId: UUID,

    val elementId: UUID,

    @field:NotBlank
    @field:Length(max = 1000)
    val comment: String
)