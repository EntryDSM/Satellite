package kr.hs.entrydsm.satellite.common

import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty

enum class DomainErrorCode(
    private val status: Int,
    private val code: String,
    private val message: String
) : CustomErrorProperty {

    PASSWORD_MISMATCH(400, "COMMON-400-1", "Password mismatched"),
    EMAIL_SUFFIX_NOT_VALID(400, "COMMON-400-2", "Email should ends with @dsm.hs.kr"),

    REFRESH_TOKEN_NOT_FOUND(404, "AUTH-404-1", "Refresh token not found"),

    STUDENT_NOT_FOUND(404, "STUDENT-404-1", "Student not found"),
    STUDENT_ALREADY_EXIST(409, "STUDENT-409-1", "Student already exist"),

    TEACHER_NOT_FOUND(404, "TEACHER-404-1", "Teacher not found"),

    MAJOR_NOT_FOUND(404, "MAJOR-404-1", "Major not found"),

    DOCUMENT_ACCESS_RIGHT(403, "DOCUMENT-403-1", "Have no access to documents"),
    DOCUMENT_NOT_FOUND(404, "DOCUMENT-404-1", "Document not found"),
    DOCUMENT_ALREADY_EXIST(409, "DOCUMENT-409-1", "Document already exist"),
    DOCUMENT_ILLEGAL_STATUS(409, "DOCUMENT-409-2", "Unable to perform that action"),

    SECRET_MISMATCH(403, "SCHOOL-403-1", "Secret mismatch"),
    LIBRARY_DOCUMENT_NOT_FOUND(404, "SCHOOL-404-1", "Library document not found"),

    FEEDBACK_NOT_FOUND(404, "FEEDBACK-404-1", "Feedback not found"),
    FEEDBACK_ALREADY_EXIST(409, "FEEDBACK-409-1", "Feedback already exist"),

    INVALID_IMAGE(400, "FILE-400-1", "Invalid image"),
    ;

    override fun status(): Int = status
    override fun code(): String = code
    override fun message(): String = message
}