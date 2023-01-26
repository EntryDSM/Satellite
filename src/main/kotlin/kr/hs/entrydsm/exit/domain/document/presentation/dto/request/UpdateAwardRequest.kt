package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.AwardElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.NotBlank

data class UpdateAwardRequest(

    @field:NotNull
    val awardList: List<AwardRequest>
) {
    data class AwardRequest(

        @field:Length(max=30)
        @field:NotBlank
        val name: String,

        @field:Length(max=30)
        @field:NotBlank
        val awardingInstitution: String,

        @field:NotNull
        val date: Date,

        @field:Length(max=255)
        val description: String?,

        @field:Length(max=255)
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