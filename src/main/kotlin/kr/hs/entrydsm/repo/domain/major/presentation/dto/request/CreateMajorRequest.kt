package kr.hs.entrydsm.repo.domain.major.presentation.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty

data class CreateMajorRequest(

    @field:Length(max = 30)
    @field:NotEmpty
    val majorName: String

)