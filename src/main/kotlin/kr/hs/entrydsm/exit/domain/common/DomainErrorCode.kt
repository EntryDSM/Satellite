package kr.hs.entrydsm.exit.domain.common

import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty

enum class DomainErrorCode(
    private val status: Int,
    private val code: String,
    private val message: String
) : kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty {

    PASSWORD_MISS_MATCHED(400, "COMMON-400-1", "PASSWORD MISMATCH"),
    EMAIL_SUFFIX_NOT_VALID(400, "COMMON-400-2", "Email not ends with @dsm.hs.kr"),

    STUDENT_NOT_FOUND(404, "STUDENT-404-1", "STUDENT NOT FOUND"),

    TEACHER_NOT_FOUND(404, "TEACHER-404-1", "TEACHER NOT FOUND"),

    COMPANY_NOT_FOUND(404, "COMPANY-404-1", "COMPANY NOT FOUND"),
    STANDBY_COMPANY_NOT_FOUND(404, "COMPANY-404-2", "STANDBY COMPANY NOT FOUND"),

    VERIFICATION_CODE_MISS_MATCHED(400, "AUTH-400-1", "VERIFICATION CODE MIS MATCH"),
    IS_NOT_VERIFIED_PHONE_NUMBER(400, "AUTH-400-2", "IS NOT VERIFIED PHONE NUMBER"),
    PHONE_NUMBER_VERIFICATION_CODE_NOT_FOUND(404, "AUTH-404-1", "PHONE NUMBER VERIFICATION CODE NOT FOUND"),
    ALREADY_VERIFIED_EXCEPTION(409, "AUTH-409-1", "IS ALREADY VERIFIED"),
    TOO_MANY_SEND_VERIFICATION_CODE(429, "AUTH-429-1", "TOO MANY SEND VERIFICATION CODE / PLEASE CONFIRM LATER"),

    DOCUMENT_NOT_FOUND(404, "DOCUMENT-404-1", "Document Not Found")
    ;

    override fun status(): Int = status
    override fun code(): String = code
    override fun message(): String = message
}