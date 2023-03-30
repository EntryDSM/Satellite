package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateDocumentRequest(
    @get:NotNull
    val majorId: UUID?
)