package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import java.util.*
import javax.validation.constraints.NotNull

data class QueryDocumentRequest(

    @field:NotNull
    val name: String? = "",

    val grade: String?,

    val classNum: String?,

    val majorId: UUID?
)