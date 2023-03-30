package kr.hs.entrydsm.satellite.domain.major.persistence

import kr.hs.entrydsm.satellite.global.entity.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "tbl_major")
@Entity
class MajorJpaEntity(

    id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    val name: String

) : BaseUUIDEntity(id)