package kr.hs.entrydsm.satellite.domain.school.facade

import kr.hs.entrydsm.satellite.domain.school.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.satellite.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.satellite.common.exception.InternalServerException
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