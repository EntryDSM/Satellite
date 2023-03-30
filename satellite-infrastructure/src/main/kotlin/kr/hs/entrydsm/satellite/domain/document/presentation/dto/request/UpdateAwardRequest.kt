package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import java.util.Date
import javax.validation.constraints.NotBlank
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import org.hibernate.validator.constraints.Length

data class UpdateAwardRequest(

    val awardList: List<AwardRequest>
) {
    data class AwardRequest(

        @field:Length(max = 30)
        @field:NotBlank
        val name: String,

        @field:Length(max = 30)
        @field:NotBlank
        val awardingInstitution: String,

        val date: Date,

        @field:Length(max = 255)
        val description: String?,

        @field:Length(max = 255)
        val url: String?,

    ) {
        fun toAwardElement() = AwardElement(
            name = name,
            awardingInstitution = awardingInstitution,
            date = date,
            description = description,
            url = url
        )
    }
}