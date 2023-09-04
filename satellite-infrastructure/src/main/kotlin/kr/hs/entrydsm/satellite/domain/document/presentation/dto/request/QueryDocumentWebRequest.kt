package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Max
import org.hibernate.validator.constraints.Length
import java.util.*

data class QueryDocumentWebRequest(

    @field:Length(max = 300)
    val name: String?,

    @field:Max(3)
    val grade: Int?,

    @field:Max(4)
    val classNum: Int?,

    val majorId: UUID?

)