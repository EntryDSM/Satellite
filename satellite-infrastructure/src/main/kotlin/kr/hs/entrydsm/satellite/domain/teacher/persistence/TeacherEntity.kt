package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_teacher")
class TeacherEntity(
    @Id
    override var id: UUID,
    override var accountId: String,
    override var password: String
) : Teacher(
    id, accountId, password
)
