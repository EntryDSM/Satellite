package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import kr.hs.entrydsm.satellite.domain.library.persistence.repository.SchoolYearRepository
import kr.hs.entrydsm.satellite.domain.library.properties.SchoolYearProperties
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort
import org.springframework.data.repository.findByIdOrNull

@Adapter
class SchoolYearPersistenceAdapter(
    private val securityPort: SecurityPort,
    private val schoolYearRepository: SchoolYearRepository,
    private val schoolYearProperties: SchoolYearProperties
) : SchoolYearPort {

    override fun save(schoolYear: SchoolYear): SchoolYearJpaEntity = schoolYear.run {
        schoolYearRepository.save(
            SchoolYearJpaEntity(
                id = id,
                year = year
            )
        )
    }

    override fun getSchoolYear(): SchoolYear =
        schoolYearRepository.findByIdOrNull(schoolYearProperties.id)!!

    override fun secretMatches(secret: String) =
        securityPort.encyptMatches(secret, schoolYearProperties.secret)

}