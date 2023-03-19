package kr.hs.entrydsm.repo.domain.major.presentation.dto.request

import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length

data class CreateMajorRequest(

    @field:Length(max = 30)
    @field:NotEmpty
    val majorName: String

)