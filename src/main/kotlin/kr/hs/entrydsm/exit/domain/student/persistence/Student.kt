package kr.hs.entrydsm.exit.domain.student.persistence

import kr.hs.entrydsm.exit.domain.image.domain.DefaultImage
import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

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
    val profileImagePath: String = DefaultImage.USER_PROFILE


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