package kr.hs.entrydsm.satellite.domain.school.spi

import kr.hs.entrydsm.satellite.domain.school.domain.SchoolYear

interface SchoolYearPort {
    fun save(schoolYear: SchoolYear): SchoolYear
    fun getSchoolYear(): SchoolYear
    fun secretMatches(secret: String): Boolean
}