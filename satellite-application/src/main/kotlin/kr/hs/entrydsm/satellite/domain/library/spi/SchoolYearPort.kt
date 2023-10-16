package kr.hs.entrydsm.satellite.domain.library.spi

import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear

interface SchoolYearPort {
    suspend fun save(schoolYear: SchoolYear): SchoolYear
    suspend fun getSchoolYear(): SchoolYear
    suspend fun checkSecretMatches(secret: String)
}