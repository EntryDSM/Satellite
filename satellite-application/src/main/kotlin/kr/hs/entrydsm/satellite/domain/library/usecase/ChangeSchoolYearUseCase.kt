package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.library.exception.SecretMismatchException
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort

@UseCase
class ChangeSchoolYearUseCase(
    private val schoolYearPort: SchoolYearPort
) {
    fun execute(year: Int, secret: String) {

        if (schoolYearPort.secretMatches(secret)) {
            throw SecretMismatchException
        }

        val schoolYear = schoolYearPort.getSchoolYear()
        schoolYearPort.save(
            schoolYear.changeYear(year)
        )
    }
}