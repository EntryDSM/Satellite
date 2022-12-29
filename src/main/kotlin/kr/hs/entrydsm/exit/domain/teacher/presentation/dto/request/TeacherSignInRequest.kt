package kr.hs.entrydsm.exit.domain.teacher.presentation.dto.request

data class TeacherSignInRequest(
    val accountId: String,

    val password: String
)