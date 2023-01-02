package kr.hs.entrydsm.exit.domain.document.persistence.element

import kr.hs.entrydsm.exit.domain.major.persistence.Major
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import java.util.*

class WriterInfoElement (

    val studentId: UUID,

    val name: String,
    val profileImagePath: String,

    val grade: String,
    val classNum: String,
    val number: String,

    val email: String,
    val major: Major

) {
    constructor(
        student: Student,
        major: Major
    ) : this(
        studentId = student.id,

        name = student.name!!,
        profileImagePath = student.profileImagePath!!,

        grade = student.grade!!,
        classNum = student.classNum!!,
        number = student.number!!,

        email = student.email,
        major = major
    )

    fun updateVariableInfo(
        profileImagePath: String,
        grade: String,
        classNum: String,
        number: String,
        major: Major
    ): WriterInfoElement {
        return copy(
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            major = major
        )
    }

    private fun copy(
        studentId: UUID = this.studentId,
        name: String = this.name,
        profileImagePath: String = this.profileImagePath,
        grade: String = this.grade,
        classNum: String = this.classNum,
        number: String  = this.number,
        email: String = this.email,
        major: Major = this.major
    ): WriterInfoElement {
        return WriterInfoElement(
            studentId = studentId,
            name = name,
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            major = major
        )
    }

}