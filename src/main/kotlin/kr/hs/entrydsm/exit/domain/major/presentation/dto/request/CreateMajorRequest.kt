package kr.hs.entrydsm.exit.domain.major.presentation.dto.request

import javax.validation.constraints.NotEmpty

data class CreateMajorRequest(

    @field:NotEmpty
    val majorName: String

)