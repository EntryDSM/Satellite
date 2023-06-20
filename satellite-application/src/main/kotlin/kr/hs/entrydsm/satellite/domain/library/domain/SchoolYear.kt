package kr.hs.entrydsm.satellite.domain.library.domain

import java.util.*

interface SchoolYear {
    val id: UUID
    var year: Int

    fun changeYear(year: Int) {
        this.year = year
    }
}