package kr.hs.entrydsm.exit.domain.teacher.presentation.dto.request

import javax.validation.constraints.NotNull


data class TeacherSignInRequest(

    
    val accountId: String,

    
    val password: String
)