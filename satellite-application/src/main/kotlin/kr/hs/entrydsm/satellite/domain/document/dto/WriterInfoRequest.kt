package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.file.domain.DefaultImages
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

open class WriterInfoRequest(
    profileImagePath: String?,
    open val majorId: UUID,
    open val email: String,
    open val grade: Int,
    open val classNum: Int,
    open val number: Int
) {
    val profileImagePath = profileImagePath ?: DefaultImages.USER_PROFILE

    fun toElement(student: Student, major: Major) =
        WriterInfoElement(
            studentId = student.id,
            name = student.name,
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = major.id,
            majorName = major.name
        )

    fun toStudent(student: Student) =
        student.apply {
            this.grade = grade
            this.classNum = classNum
            this.number = number
            this.profileImagePath = profileImagePath
        }
}