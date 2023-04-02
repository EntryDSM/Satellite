package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.UUID

open class SchoolYear(
    open val id: UUID,
    open val year: Int
) : Domain {
    fun copy(
        id: UUID = this.id,
        year: Int = this.year
    ) = SchoolYear(
        id = id,
        year = year
    )
}