package kr.hs.entrydsm.repo.domain.school.facade

import kr.hs.entrydsm.repo.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.repo.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.repo.global.exception.InternalServerException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SchoolYearFacade(
    private val schoolYearProperties: SchoolYearProperties,
    private val schoolYearRepository: SchoolYearRepository
) {
    fun getSchoolYear() =
        (schoolYearRepository.findByIdOrNull(schoolYearProperties.id) ?: throw InternalServerException).year
}