package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import java.util.*
import java.util.regex.Pattern

open class Student(
    open val id: UUID = UUID.randomUUID(),
    open val email: String,
    open val name: String,
    open val grade: String,
    open val classNum: String,
    open val number: String,
    open val profileImagePath: String
) {

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