package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_teacher")
class TeacherJpaEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    override val id: UUID,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    override val accountId: String,

    @Column(columnDefinition = "VARCHAR(60)", nullable = false)
    override val password: String

) : Teacher(
    id = id,
    accountId = accountId,
    password = password
)