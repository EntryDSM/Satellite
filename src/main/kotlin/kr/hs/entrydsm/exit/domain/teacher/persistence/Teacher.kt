package kr.hs.entrydsm.exit.domain.teacher.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_teacher")
class Teacher(

    id: UUID? = null,

    @NotNull
    @Length(min = 5)
    val accountId: String?,

    @field: NotNull
    @Length(min = 8, max = 60)
    val password: String

): BaseUUIDEntity(id)