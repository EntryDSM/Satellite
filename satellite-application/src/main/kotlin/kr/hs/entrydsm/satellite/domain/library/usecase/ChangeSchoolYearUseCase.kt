package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort

@UseCase
class ChangeSchoolYearUseCase(
    private val schoolYearPort: SchoolYearPort
) {
    suspend fun execute(year: Int, secret: String) {

        schoolYearPort.checkSecretMatches(secret)

        val schoolYear = schoolYearPort.getSchoolYear()
        schoolYearPort.save(
            schoolYear.apply { changeYear(year) }
        )
    }
}