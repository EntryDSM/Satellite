package kr.hs.entrydsm.satellite.global.error.response

data class DefaultErrorResponse(
    val status: Int,
    val message: String,
    val code: String
)