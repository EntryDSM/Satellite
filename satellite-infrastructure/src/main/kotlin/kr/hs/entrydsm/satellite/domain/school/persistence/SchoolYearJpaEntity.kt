package kr.hs.entrydsm.satellite.domain.school.persistence

import kr.hs.entrydsm.satellite.global.entity.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "tbl_school_year")
@Entity
class SchoolYearJpaEntity(

    id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val year: Int

) : BaseUUIDEntity(id)