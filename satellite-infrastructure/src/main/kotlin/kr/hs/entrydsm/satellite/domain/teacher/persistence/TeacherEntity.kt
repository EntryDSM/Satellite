package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_teacher")
data class TeacherEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override val accountId: String,
    override val password: String
) : Teacher, BaseUUIDEntity(id)
