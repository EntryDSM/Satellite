package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

class SchoolYear(
    val id: UUID,
    val year: Int
) : Domain {

    fun changeYear(year: Int) = copy(year = year)

    fun copy(
        id: UUID = this.id,
        year: Int = this.year
    ) = SchoolYear(
        id = id,
        year = year
    )
}