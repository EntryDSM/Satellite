package kr.hs.entrydsm.satellite.domain.student.persistence

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_student")
class StudentJpaEntity(

    @Id
    override val id: UUID,

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    override val email: String,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    override val name: String,

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    override val grade: String,

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    override val classNum: String,

    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    override val number: String,

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
)