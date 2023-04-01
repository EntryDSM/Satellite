package kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request

import javax.validation.constraints.NotBlank

data class TeacherSignInRequest(

    @NotBlank
    val accountId: String?,

    @NotBlank
    val password: String?
)