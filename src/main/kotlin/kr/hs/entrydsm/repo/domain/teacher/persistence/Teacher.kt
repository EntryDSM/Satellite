package kr.hs.entrydsm.repo.domain.teacher.persistence

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull

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

) : BaseUUIDEntity(id)