package kr.hs.entrydsm.satellite.domain.document.domain.element

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

class WriterInfoElement(

    elementId: UUID? = null,

    val studentId: UUID,
    val name: String,
    val email: String,
    var profileImagePath: String,

    val grade: Int,
    val classNum: Int,
    val number: Int,

    val majorId: UUID,
    val majorName: String,

    val url: String? = null

) : AbstractElement(elementId) {

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
            grade: Int,
            classNum: Int,
            number: Int,
        ): Int = Integer.valueOf(
            grade.toString() + classNum.toString() + String.format("%02d", number)
        )
    }
}