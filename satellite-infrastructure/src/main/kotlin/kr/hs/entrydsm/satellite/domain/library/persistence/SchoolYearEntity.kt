package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_school_year")
data class SchoolYearEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override var year: Int
) : SchoolYear, BaseUUIDEntity(id)