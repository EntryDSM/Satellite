package kr.hs.entrydsm.satellite.domain.major.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity

@Table(name = "tbl_major")
@Entity
class Major(

    id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    val name: String

) : BaseUUIDEntity(id)