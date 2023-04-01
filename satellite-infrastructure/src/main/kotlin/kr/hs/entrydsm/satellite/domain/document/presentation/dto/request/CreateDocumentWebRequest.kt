package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import java.util.*
import javax.validation.constraints.NotNull

data class CreateDocumentWebRequest(
    @get:NotNull
    val majorId: UUID?
)