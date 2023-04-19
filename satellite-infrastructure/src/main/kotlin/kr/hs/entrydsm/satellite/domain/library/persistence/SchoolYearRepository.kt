package kr.hs.entrydsm.satellite.domain.library.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface SchoolYearRepository : ReactiveCrudRepository<SchoolYearEntity, UUID>