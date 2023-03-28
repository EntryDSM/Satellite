package kr.hs.entrydsm.satellite.domain.student.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity

@Entity
@Table(name = "tbl_student")
class Student(

    id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    val email: String,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    val name: String,

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    val grade: String,

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    val classNum: String,

    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    val number: String,

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    val profileImagePath: String

) : BaseUUIDEntity(id) {

    fun updateVariableInfo(
        grade: String,
        classNum: String,
        number: String,
        profileImagePath: String
    ): Student = Student(
        id = this.id,
        email = this.email,
        name = this.name,
        grade = grade,
        classNum = classNum,
        number = number,
        profileImagePath = profileImagePath
    )
}