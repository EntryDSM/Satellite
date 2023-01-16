package kr.hs.entrydsm.exit.domain.student.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import kr.hs.entrydsm.exit.global.util.RegexUtil
import org.intellij.lang.annotations.Pattern
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_student")
class Student(

    id: UUID? = null,
    val email: String,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    val name: String,

    @Pattern(RegexUtil.NUMBER_EXP)
    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    val grade: String,

    @Pattern(RegexUtil.NUMBER_EXP)
    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    val classNum: String,

    @Pattern(RegexUtil.NUMBER_EXP)
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    val number: String,

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    val profileImagePath: String

) : BaseUUIDEntity(id) {