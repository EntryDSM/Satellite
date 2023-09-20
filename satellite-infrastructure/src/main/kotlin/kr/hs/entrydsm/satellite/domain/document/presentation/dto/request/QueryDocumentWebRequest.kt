package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import org.hibernate.validator.constraints.Length
import java.util.*

data class QueryDocumentWebRequest(

    @field:Length(max = 300)
    val name: String?,

    @field:Max(3)
    @field:Min(1)
    val grade: Int?,

    @field:Max(4)
    @field:Min(1)
    val classNum: Int?,

    val majorId: UUID?

)