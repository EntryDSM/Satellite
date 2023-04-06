package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

open class WriterInfoRequest(
    open val profileImagePath: String,
    open val majorId: UUID,
    open val email: String,
    open val grade: Int,
    open val classNum: Int,
    open val number: Int
) {
    fun toElement(student: Student, major: Major): WriterInfoElement {
        return WriterInfoElement(
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
    }
}