package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.IntroduceElement
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty

data class UpdateIntroduceRequest(

    @field:Length(max=100)
    @field:NotEmpty
    val heading: String,

    @field:NotEmpty
    val introduce: String
) {
    fun toIntroduceElement() = IntroduceElement(
        heading = heading,
        introduce = introduce
    )
}