package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface SchoolYear {
    val id: UUID
    var year: Int

    fun changeYear(year: Int) {
        this.year = year
    }
}

data class SchoolYearDomain(
    override val id: UUID,
    override var year: Int
) : SchoolYear, Domain