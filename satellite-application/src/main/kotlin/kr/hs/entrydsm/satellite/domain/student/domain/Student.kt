package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*
import java.util.regex.Pattern

interface Student {
    val id: UUID
    val email: String
    val name: String
    var grade: Int
    var classNum: Int
    var number: Int
    var profileImagePath: String

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

data class StudentDomain(
    override val id: UUID = UUID(0, 0),
    override val email: String,
    override val name: String,
    override var grade: Int,
    override var classNum: Int,
    override var number: Int,
    override var profileImagePath: String
) : Student, Domain