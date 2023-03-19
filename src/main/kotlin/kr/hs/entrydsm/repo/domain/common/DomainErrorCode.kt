package kr.hs.entrydsm.repo.domain.common

import kr.hs.entrydsm.repo.global.error.custom.CustomErrorProperty

enum class DomainErrorCode(
    private val status: Int,
    private val code: String,
    private val message: String
) : CustomErrorProperty {

    PASSWORD_MISMATCHED(400, "COMMON-400-1", "Password mismatched"),
    EMAIL_SUFFIX_NOT_VALID(400, "COMMON-400-2", "Email not ends with @dsm.hs.kr"),

    VERIFICATION_CODE_MISMATCHED(400, "AUTH-400-1", "Verification code mismatched"),
    NOT_VERIFIED(400, "AUTH-400-2", "Is not verified phone number"),
    VERIFICATION_CODE_NOT_FOUND(404, "AUTH-404-1", "Phone number verification code not found"),
    ALREADY_VERIFIED(409, "AUTH-409-1", "Is already verified"),
    TOO_MANY_SEND_VERIFICATION_CODE(429, "AUTH-429-1", "Too many send verification code / Please confirm later"),

    STUDENT_NOT_FOUND(404, "STUDENT-404-1", "Student not found"),
    STUDENT_ALREADY_EXIST(409, "STUDENT-409-1", "Student already exist"),

    TEACHER_NOT_FOUND(404, "TEACHER-404-1", "Teacher not found"),

    COMPANY_NOT_FOUND(404, "COMPANY-404-1", "Company not found"),
    STANDBY_COMPANY_NOT_FOUND(404, "COMPANY-404-2", "Stand by company not found"),
    COMPANY_INIT_REQUIRED(422, "COMPANY-422-1", "Company init required"),

    MAJOR_NOT_FOUND(404, "MAJOR-404-1", "Major not found"),

    DOCUMENT_ACCESS_RIGHT(403, "DOCUMENT-403-1", "Have no access to documents"),
    DOCUMENT_NOT_FOUND(404, "DOCUMENT-404-1", "Document not found"),
    DOCUMENT_ALREADY_EXIST(409, "DOCUMENT-409-1", "Document already exist"),
    ILLEGAL_STATUS(409, "DOCUMENT-409-2", "Unable to perform that action"),

    FEEDBACK_NOT_FOUND(404, "FEEDBACK-404-1", "Feedback not found"),
    FEEDBACK_ALREADY_EXIST(409, "FEEDBACK-409-1", "Feedback already exist"),

    INVALID_IMAGE(400, "IMAGE-400-1", "Invalid image"),
    ;

    override fun status(): Int = status
    override fun code(): String = code
    override fun message(): String = message
}