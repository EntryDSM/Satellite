package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import org.jetbrains.annotations.NotNull
import java.util.*

data class CreateDocumentRequest(

    @field:NotNull
    val majorId: UUID
)