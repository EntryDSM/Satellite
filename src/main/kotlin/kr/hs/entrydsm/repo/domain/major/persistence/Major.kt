package kr.hs.entrydsm.repo.domain.major.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity

@Table(name = "tbl_major")
@Entity
class Major(

    id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    val name: String

) : BaseUUIDEntity(id)