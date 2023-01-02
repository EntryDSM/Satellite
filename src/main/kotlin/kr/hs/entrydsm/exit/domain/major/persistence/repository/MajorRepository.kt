package kr.hs.entrydsm.exit.domain.major.persistence.repository

import kr.hs.entrydsm.exit.domain.major.persistence.Major
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MajorRepository: CrudRepository<Major, UUID>