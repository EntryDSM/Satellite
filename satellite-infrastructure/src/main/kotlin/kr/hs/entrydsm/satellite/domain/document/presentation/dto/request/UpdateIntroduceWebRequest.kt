package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.document.dto.IntroduceRequest
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty

data class UpdateIntroduceWebRequest(

    @field:Length(max = 70)
    @field:NotEmpty
    override val heading: String,

    @field:Length(max = 250)
    @field:NotEmpty
    override val introduce: String

) : IntroduceRequest(heading, introduce)