package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.IntroduceElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.NotEmpty

data class UpdateIntroduceRequest(

    @field:NotNull
    val documentId: UUID,

    @field:Length(max=100)
    @field:NotEmpty
    val heading: String,

    @field:NotEmpty
    val introduce: String
) {
    fun toIntroduceElement(): IntroduceElement = IntroduceElement(
        heading = heading,
        introduce = introduce
    )

}