package kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request

import org.hibernate.validator.constraints.Length
import java.util.UUID
import javax.validation.constraints.NotBlank

data class CreateFeedbackRequest(
    
    val documentId: UUID,
    
    val elementId: UUID,

    @field:NotBlank
    @field:Length(max = 1000)
    val comment: String
)
