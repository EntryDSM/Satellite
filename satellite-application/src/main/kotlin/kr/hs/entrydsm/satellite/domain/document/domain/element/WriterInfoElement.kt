package kr.hs.entrydsm.satellite.domain.document.domain.element

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

data class WriterInfoElement(

    val studentId: UUID,
    val name: String,
    val email: String,
    val profileImagePath: String,

    val grade: String,
    val classNum: String,
    val number: String,

    val majorId: UUID,
    val majorName: String

) : AbstractElement() {

    override val elementName: String
        get() = "내 정보"

    val studentNumber: Int
        get() = toStudentNumber(grade, classNum, number)

    constructor(
        student: Student,
        major: Major
    ) : this(
        studentId = student.id,
        name = student.name,
        profileImagePath = student.profileImagePath,
        grade = student.grade,
        classNum = student.classNum,
        number = student.number,
        email = student.email,
        majorId = major.id,
        majorName = major.name
    )

    companion object {
        fun toStudentNumber(
            grade: String,
            classNum: String,
            number: String
        ): Int = Integer.valueOf(
            grade + classNum + String.format("%02d", Integer.parseInt(number))
        )
    }
}