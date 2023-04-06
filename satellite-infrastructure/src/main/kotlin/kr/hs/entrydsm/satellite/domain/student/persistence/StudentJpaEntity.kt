package kr.hs.entrydsm.satellite.domain.student.persistence

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_student")
class StudentJpaEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    override val id: UUID,

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    override val email: String,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    override val name: String,

    @Column(columnDefinition = "INT", nullable = false)
    override val grade: Int,

    @Column(columnDefinition = "INT", nullable = false)
    override val classNum: Int,

    @Column(columnDefinition = "INT", nullable = false)
    override val number: Int,

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    override val profileImagePath: String

) : Student(
    id = id,
    email = email,
    name = name,
    grade = grade,
    classNum = classNum,
    number = number,
    profileImagePath = profileImagePath
) {
    companion object {
        fun of(student: Student) = student.run {
            StudentJpaEntity(
                id = id,
                email = email,
                name = name,
                grade = grade,
                classNum = classNum,
                number = number,
                profileImagePath = profileImagePath
            )
        }
    }
}