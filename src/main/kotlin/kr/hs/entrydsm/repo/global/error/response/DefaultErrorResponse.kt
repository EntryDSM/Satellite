package kr.hs.entrydsm.repo.global.error.response

data class DefaultErrorResponse(
    val status: Int,
    val message: String,
    val code: String
)