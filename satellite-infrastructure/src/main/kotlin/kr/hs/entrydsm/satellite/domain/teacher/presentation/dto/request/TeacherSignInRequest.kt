package kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request

data class TeacherSignInRequest(

    val accountId: String,

    val password: String
)