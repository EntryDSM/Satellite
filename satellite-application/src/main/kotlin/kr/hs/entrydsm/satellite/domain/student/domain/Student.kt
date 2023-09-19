package kr.hs.entrydsm.satellite.domain.student.domain

import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface Student {
    val id: UUID
    val email: String
    val name: String
    var grade: Int
    var classNum: Int
    var number: Int
    var profileImagePath: String

    fun changeGcn(
        grade: Int,
        classNum: Int,
        number: Int
    ) = this.apply {
        this.grade = grade
        this.classNum = classNum
        this.number = number
    }

    fun changeProfileImagePath(
        profileImagePath: String
    ) = this.apply {
        this.profileImagePath = profileImagePath
    }

    companion object {
        private const val schoolEmail = "@dsm.hs.kr"

        fun checkEmailSuffix(email: String) {
            if (!email.endsWith(schoolEmail)) {
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