package kr.hs.entrydsm.exit.domain.student.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import com.fasterxml.uuid.Generators
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_student")
class Student(

    override val id: UUID,

    @field: NotNull
    val email: String,

    val name: String?,

    val grade: String?,

    val classNum: String?,

    val number: String?,

    val major: String?,

    val profileImagePath: String?

) : BaseUUIDEntity() {
    constructor(email: String) : this(
        Generators.timeBasedGenerator().generate(), email,
        null, null, null, null, null, null
    )
}