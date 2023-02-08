package kr.hs.entrydsm.exit.domain.auth.presentation.dto.request

data class VerifyCodeRequest(
    val key: String,
    val code: String
)