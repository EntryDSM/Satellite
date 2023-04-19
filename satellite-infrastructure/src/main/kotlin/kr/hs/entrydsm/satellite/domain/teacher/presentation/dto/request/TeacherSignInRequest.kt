package kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request

import javax.validation.constraints.NotBlank

data class TeacherSignInRequest(

    @field:NotBlank
    val accountId: String?,

    @field:NotBlank
    val password: String?
)