package kr.hs.entrydsm.repo.domain.auth.presentation.dto.request

data class VerifyCodeRequest(
    val key: String,
    val code: String
)