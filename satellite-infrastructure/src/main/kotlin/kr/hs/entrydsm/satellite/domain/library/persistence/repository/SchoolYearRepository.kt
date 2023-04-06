package kr.hs.entrydsm.satellite.domain.library.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.library.persistence.SchoolYearJpaEntity
import org.springframework.data.repository.CrudRepository

interface SchoolYearRepository : CrudRepository<SchoolYearJpaEntity, UUID>