package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*
import java.util.regex.Pattern

class Student(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String,
    val grade: String,
    val classNum: String,
    val number: String,
    val profileImagePath: String
) : Domain {

    fun copy(
        id: UUID = this.id,
        email: String = this.email,
        name: String = this.name,
        grade: String = this.grade,
        classNum: String = this.classNum,
        number: String = this.number,
        profileImagePath: String = this.profileImagePath
    ) = Student(
        id = id,
        email = email,
        name = name,
        grade = grade,
        classNum = classNum,
        number = number,
        profileImagePath = profileImagePath
    )

    companion object {

        private const val schoolEmail = "@dsm.hs.kr"
        private const val EMAIL_SUFFIX_EXP = "^[a-zA-Z0-9.]+$schoolEmail$"

        fun checkEmailSuffix(email: String) {
            if (!Pattern.matches(EMAIL_SUFFIX_EXP, email)) {
                throw EmailSuffixNotValidException
            }
        }
    }
}