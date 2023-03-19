package kr.hs.entrydsm.repo.global.error

import kr.hs.entrydsm.repo.global.error.custom.CustomErrorProperty

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String,
    private val code: String
) : CustomErrorProperty {

    EXPIRED_JWT(401, "COMMON-401-1", "Expired jwt"),
    INVALID_JWT(401, "COMMON-401-2", "Invalid jwt"),
    UNEXPECTED_JWT(401, "COMMON-401-3", "Unexpected token"),

    IMAGE_EXTENSION_INVALID(400, "COMMON-400-1", "Image extension invalid"),
    PDF_EXTENSION_INVALID(400, "COMMON-400-1", "Pdf extension invalid"),
    IMAGE_NOT_FOUND(404, "COMMON-404-1", "Image not found"),

    BAD_REQUEST(400, "COMMON-400-1", "Bad request"),
    FORBIDDEN(403, "COMMON-403-1", "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal server error")
    ;

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = code
}