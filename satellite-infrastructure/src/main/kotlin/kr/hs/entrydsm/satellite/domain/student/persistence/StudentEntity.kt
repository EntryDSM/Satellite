package kr.hs.entrydsm.satellite.domain.student.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_student")
data class StudentEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override val email: String,
    override val name: String,
    override var grade: Int,
    override var classNum: Int,
    override var number: Int,
    override var profileImagePath: String
) : Student, BaseUUIDEntity(id)