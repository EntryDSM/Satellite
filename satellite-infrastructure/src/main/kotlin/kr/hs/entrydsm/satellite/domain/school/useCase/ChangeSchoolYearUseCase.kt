package kr.hs.entrydsm.satellite.domain.school.useCase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.school.exception.SecretMismatchException
import kr.hs.entrydsm.satellite.domain.school.persistence.SchoolYearJpaEntity
import kr.hs.entrydsm.satellite.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.satellite.domain.school.spi.SchoolYearPort

@UseCase
class ChangeSchoolYearUseCase(
    private val schoolYearPort: SchoolYearPort,
    private val schoolYearRepository: SchoolYearRepository
) {
    fun execute(year: Int, secret: String) {

        if (schoolYearPort.secretMatches(secret)) {
            throw SecretMismatchException
        }

        val schoolYear = schoolYearPort.getSchoolYear()
        schoolYearPort.save(
            schoolYear.copy(year = year)
        )
    }
}