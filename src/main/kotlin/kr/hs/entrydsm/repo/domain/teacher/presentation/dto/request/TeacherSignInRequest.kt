package kr.hs.entrydsm.repo.domain.teacher.presentation.dto.request

data class TeacherSignInRequest(

    val accountId: String,

    val password: String
)