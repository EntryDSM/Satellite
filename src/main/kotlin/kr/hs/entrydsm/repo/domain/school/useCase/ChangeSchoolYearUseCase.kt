package kr.hs.entrydsm.repo.domain.school.useCase

import kr.hs.entrydsm.repo.domain.school.persistence.SchoolYear
import kr.hs.entrydsm.repo.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.repo.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.repo.global.exception.ForbiddenException
import kr.hs.entrydsm.repo.global.exception.InternalServerException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
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