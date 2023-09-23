package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

open class WriterInfoRequest(
    open val majorId: UUID?,
    open val email: String,
    open val grade: Int,
    open val classNum: Int,
    open val number: Int,
    open val skillSet: List<String>,
    open val url: String?
) {

    fun toElement(elementId: UUID, student: Student, major: Major) =
        WriterInfoElement(
            elementId = elementId,
            studentId = student.id,
            name = student.name,
            profileImagePath = student.profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            skillSet = skillSet,
            majorId = major.id,
            majorName = major.name,
            url = url
        )


    fun toElement(elementId: UUID, student: Student, majorName: String, majorId: UUID) =
        WriterInfoElement(
            elementId = elementId,
            studentId = student.id,
            name = student.name,
            profileImagePath = student.profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            skillSet = skillSet,
            majorId = majorId,
            majorName = majorName,
            url = url
        )

    fun toStudent(student: Student) =
        student.changeGcn(grade, classNum, number)
}