package kr.hs.entrydsm.exit.domain.teacher.presentation.dto.request

import javax.validation.constraints.NotNull


data class TeacherSignInRequest(

    @field:NotNull
    val accountId: String,

    @field:NotNull
    val password: String
)