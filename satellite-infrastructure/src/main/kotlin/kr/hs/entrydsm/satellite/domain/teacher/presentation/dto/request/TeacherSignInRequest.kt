package kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request

import javax.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class TeacherSignInRequest(

    @field:NotBlank
    @field:Length(max = 300)
    val accountId: String?,

    @field:NotBlank
    @field:Length(max = 300)
    val password: String?
)