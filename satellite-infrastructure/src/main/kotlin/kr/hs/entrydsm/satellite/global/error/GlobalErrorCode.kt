package kr.hs.entrydsm.satellite.global.error

import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String,
    private val code: String
) : CustomErrorProperty {

    EXPIRED_TOKEN(401, "TOKEN-401-1", "Expired jwt"),
    INVALID_TOKEN(401, "TOKEN-401-2", "Invalid jwt"),
    UNEXPECTED_TOKEN(401, "TOKEN-401-3", "Unexpected token"),

    INVALID_FILE(400, "FILE-400-1", "Invalid file"),
    INVALID_EXTENSION(400, "FILE-400-2", "Invalid extension"),
    IMAGE_NOT_FOUND(404, "FILE-404-1", "Image not found"),

    BAD_REQUEST(400, "COMMON-400-1", "Bad request"),
    FORBIDDEN(403, "COMMON-403-1", "Forbidden"),
    METHOD_NOT_ALLOWED(405, "COMMON-405-1", "Method not allowed"),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal server error")
    ;

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = code
}