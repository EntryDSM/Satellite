package kr.hs.entrydsm.satellite.domain.school.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.school.persistence.SchoolYearJpaEntity
import org.springframework.data.repository.CrudRepository

interface SchoolYearRepository : CrudRepository<SchoolYearJpaEntity, UUID>