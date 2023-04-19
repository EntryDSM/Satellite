package kr.hs.entrydsm.satellite.domain.major.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_major")
data class MajorEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override val name: String
) : Major, BaseUUIDEntity(id)