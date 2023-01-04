package kr.hs.entrydsm.exit.domain.company.persistence.repository

import kr.hs.entrydsm.exit.domain.company.persistence.StandbyCompany
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StandbyCompanyRepository : CrudRepository<StandbyCompany, UUID>