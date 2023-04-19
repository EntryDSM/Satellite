package kr.hs.entrydsm.satellite.domain.library.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import kr.hs.entrydsm.satellite.domain.library.properties.SchoolYearProperties
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort

@Adapter
class SchoolYearPersistenceAdapter(
    private val securityPort: SecurityPort,
    private val schoolYearProperties: SchoolYearProperties,
    private val schoolYearRepository: SchoolYearRepository,
    private val objectMapper: ObjectMapper
) : SchoolYearPort {

    override suspend fun save(schoolYear: SchoolYear) = schoolYear.also {
        schoolYearRepository.save(objectMapper.convertValue(schoolYear)).awaitSingle()
    }

    override suspend fun getSchoolYear(): SchoolYearEntity =
        schoolYearRepository.findById(schoolYearProperties.id).awaitSingle()

    override suspend fun secretMatches(secret: String) =
        securityPort.encyptMatches(secret, schoolYearProperties.secret)

}