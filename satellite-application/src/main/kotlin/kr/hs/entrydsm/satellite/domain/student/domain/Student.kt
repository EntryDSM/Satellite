package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import java.util.*
import java.util.regex.Pattern

data class Student(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String,
    val grade: String,
    val classNum: String,
    val number: String,
    val profileImagePath: String
) {
    companion object {

        const val PASSWORD_EXP = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+\$).{8,30}"

        private const val schoolEmail = "@dsm.hs.kr"
        private const val EMAIL_SUFFIX_EXP = "^[a-zA-Z0-9.]+$schoolEmail$"

        fun checkEmailSuffix(email: String) {
            if (!Pattern.matches(EMAIL_SUFFIX_EXP, email)) {
                throw EmailSuffixNotValidException
            }
        }
    }
}