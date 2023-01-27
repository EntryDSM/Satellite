package kr.hs.entrydsm.exit.domain.document.persistence.element

import kr.hs.entrydsm.exit.domain.major.persistence.Major
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import java.util.*

class WriterInfoElement (

    val elementId: UUID = UUID.randomUUID(),
    val studentId: UUID,

    val name: String,
    val profileImagePath: String,

    val grade: String,
    val classNum: String,
    val number: String,

    val email: String,

    val majorId: UUID,
    val majorName: String // TODO: Element로 묶고싶은데 묶으면 querydsl에서 SimplePath 처리돼서 query를 못함, 해결방안 찾을시 수정

) {

    val studentNumber: String
        get() = grade + classNum + String.format("%02d", Integer.parseInt(number))

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

    fun updateVariableInfo(
        profileImagePath: String,
        grade: String,
        classNum: String,
        number: String,
        email: String,
        major: Major
    ): WriterInfoElement {
        return copy(
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = major.id,
            majorName = major.name
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
        majorId: UUID = this.majorId,
        majorName: String = this.majorName
    ): WriterInfoElement {
        return WriterInfoElement(
            studentId = studentId,
            name = name,
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = majorId,
            majorName = majorName
        )
    }

}