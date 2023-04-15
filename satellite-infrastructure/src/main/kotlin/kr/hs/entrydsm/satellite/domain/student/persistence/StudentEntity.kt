package kr.hs.entrydsm.satellite.domain.student.persistence

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_student")
class StudentEntity(
    @Id
    override val id: UUID,
    override val email: String,
    override val name: String,
    override val grade: Int,
    override val classNum: Int,
    override val number: Int,
    override val profileImagePath: String
) : Student(
    id, email, name, grade, classNum, number, profileImagePath
) {
    companion object {
        fun of(student: Student) = student.run {
            StudentEntity(
                id, email, name, grade, classNum, number, profileImagePath
            )
        }
    }
}