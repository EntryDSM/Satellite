package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_teacher")
class TeacherEntity(
    @Id
    override val id: UUID,
    override val accountId: String,
    override val password: String
) : Teacher(
    id, accountId, password
)