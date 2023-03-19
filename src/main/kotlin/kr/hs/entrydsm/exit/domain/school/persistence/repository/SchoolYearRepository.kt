package kr.hs.entrydsm.exit.domain.school.persistence.repository

import kr.hs.entrydsm.exit.domain.school.persistence.SchoolYear
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SchoolYearRepository : CrudRepository<SchoolYear, UUID>