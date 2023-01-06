package kr.hs.entrydsm.exit.domain.common

import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty

enum class DomainErrorCode(
    private val status: Int,
    private val code: String,
    private val message: String
) : CustomErrorProperty {

    PASSWORD_MISMATCHED(400, "COMMON-400-1", "Password Mismatched"),
    EMAIL_SUFFIX_NOT_VALID(400, "COMMON-400-2", "Email not ends with @dsm.hs.kr"),

    STUDENT_NOT_FOUND(404, "STUDENT-404-1", "Student Not Found"),

    TEACHER_NOT_FOUND(404, "TEACHER-404-1", "Teacher Not Found"),

    COMPANY_NOT_FOUND(404, "COMPANY-404-1", "Company Not Found"),
    STANDBY_COMPANY_NOT_FOUND(404, "COMPANY-404-2", "Stand by Company Not Found"),

    VERIFICATION_CODE_MISMATCHED(400, "AUTH-400-1", "Verification code"),
    IS_NOT_VERIFIED_PHONE_NUMBER(400, "AUTH-400-2", "is Not Verified Phone Number"),
    PHONE_NUMBER_VERIFICATION_CODE_NOT_FOUND(404, "AUTH-404-1", "Phone Number Verification code Not Found"),
    ALREADY_VERIFIED_EXCEPTION(409, "AUTH-409-1", "Is Alreadt verified"),
    TOO_MANY_SEND_VERIFICATION_CODE(429, "AUTH-429-1", "Too Many Send Verification Code / Please Confirm Later"),

    DOCUMENT_NOT_FOUND(404, "DOCUMENT-404-1", "Document Not Found"),
    DOCUMENT_ALREADY_EXIST(409, "DOCUMENT-409-1", "Document Already Exist"),

    MAJOR_NOT_FOUND(404, "MAJOR-404-1", "Major Not Found"),

    INVALID_IMAGE(400, "IMAGE-400-1", "Invalid Image")
    ;

    override fun status(): Int = status
    override fun code(): String = code
    override fun message(): String = message
}