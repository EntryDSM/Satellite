package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.domain.document.dto.AwardRequest
import org.hibernate.validator.constraints.Length
import java.util.*

data class UpdateAwardWebRequest(
    @field:Size(max = 10)
    val awardList: List<AwardWebRequest>
) {
    data class AwardWebRequest(

        override val elementId: UUID?,

        @field:Length(max = 30)
        @field:NotBlank
        override val name: String,

        @field:Length(max = 30)
        @field:NotBlank
        override val awardingInstitution: String,

        override val date: Date,

        @field:Length(max = 80)
        override val description: String?,

    ) : AwardRequest(elementId, name, awardingInstitution, date, description)
}