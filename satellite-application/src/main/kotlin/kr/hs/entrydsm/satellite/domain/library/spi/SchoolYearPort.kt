package kr.hs.entrydsm.satellite.domain.library.spi

import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear

interface SchoolYearPort {
    fun save(schoolYear: SchoolYear): SchoolYear
    fun getSchoolYear(): SchoolYear
    fun secretMatches(secret: String): Boolean
}