package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*
import java.util.regex.Pattern

data class Student(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImagePath: String
) : Domain {

    protected constructor(): this(UUID(0,0), "", "", 1, 1, 1, "")

    companion object {

        private const val schoolEmail = "@dsm.hs.kr"
        const val EMAIL_SUFFIX_EXP = "^[a-zA-Z0-9.]+$schoolEmail$"

        fun checkEmailSuffix(email: String) {
            if (!Pattern.matches(EMAIL_SUFFIX_EXP, email)) {
                throw EmailSuffixNotValidException
            }
        }
    }
}