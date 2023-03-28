package kr.hs.entrydsm.satellite.domain.school.useCase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.school.persistence.SchoolYear
import kr.hs.entrydsm.satellite.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.satellite.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.satellite.common.exception.ForbiddenException
import kr.hs.entrydsm.satellite.common.exception.InternalServerException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class ChangeSchoolYearUseCase(
    private val schoolYearProperties: SchoolYearProperties,
    private val schoolYearRepository: SchoolYearRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun execute(year: Int, secret: String) {
        if (passwordEncoder.matches(secret, schoolYearProperties.secret)) {
            throw ForbiddenException
        }

        val schoolYear = schoolYearRepository.findByIdOrNull(schoolYearProperties.id) ?: throw InternalServerException

        schoolYearRepository.save(
            SchoolYear(
                id = schoolYear.id,
                year = year
            )
        )
    }
}