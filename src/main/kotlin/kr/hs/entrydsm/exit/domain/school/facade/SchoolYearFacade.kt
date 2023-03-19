package kr.hs.entrydsm.exit.domain.school.facade

import kr.hs.entrydsm.exit.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.exit.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.exit.global.exception.InternalServerException
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