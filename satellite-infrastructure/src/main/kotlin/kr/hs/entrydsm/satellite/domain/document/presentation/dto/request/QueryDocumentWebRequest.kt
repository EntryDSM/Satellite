package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Max
import java.util.*

data class QueryDocumentWebRequest(

    val name: String?,

    @field:Max(3)
    val grade: Int?,

    @field:Max(4)
    val classNum: Int?,

    val majorId: UUID?

)