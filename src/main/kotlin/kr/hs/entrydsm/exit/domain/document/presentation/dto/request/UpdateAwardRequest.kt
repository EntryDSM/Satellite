package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.AwardElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.NotEmpty

data class UpdateAwardRequest(

    @field:NotNull
    val documentId: UUID,

    @field:NotNull
    val awardList: List<AwardRequest>
) {
    data class AwardRequest(

        @field:Length(max=30)
        @field:NotEmpty
        val name: String,

        @field:Length(max=30)
        @field:NotEmpty
        val awardingInstitution: String,

        @field:NotNull
        val awardDate: Date,

        @field:Length(max=255)
        val description: String?,

        @field:Length(max=255)
        val url: String?,

    ) {
        fun toAwardElement(): AwardElement {
            return this.run{ AwardElement(
                name = name,
                awardingInstitution = awardingInstitution,
                date = awardDate,
                description = description,
                url = url
            ) }
        }
    }
}