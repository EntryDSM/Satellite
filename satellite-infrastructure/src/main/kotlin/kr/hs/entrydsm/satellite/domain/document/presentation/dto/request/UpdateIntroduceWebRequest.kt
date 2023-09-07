package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotNull
import kr.hs.entrydsm.satellite.domain.document.dto.IntroduceRequest
import org.hibernate.validator.constraints.Length

data class UpdateIntroduceWebRequest(

    @field:Length(max = 70)
    @field:NotNull
    override val heading: String,

    @field:Length(max = 250)
    @field:NotNull
    override val introduce: String

) : IntroduceRequest(heading, introduce)