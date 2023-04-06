package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

data class SchoolYear(
    val id: UUID,
    val year: Int
) : Domain {

    fun changeYear(year: Int) = copy(year = year)

    protected constructor(): this(UUID(0,0), 2023)
}