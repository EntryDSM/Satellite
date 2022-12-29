package kr.hs.entrydsm.exit.global.error

import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String,
    private val code: String
) : kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty {

    EXPIRED_JWT(401, "COMMON-401-1", "Expired Jwt"),
    INVALID_JWT(401, "COMMON-401-2", "Invalid Jwt"),
    UNEXPECTED_JWT(401, "COMMON-401-3", "Unexpected Token"),
    INVALID_CLAIM_JWT(401, "COMMON-401-4", "Invalid Claim Jwt"),

    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error")
    ;


    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = code
}