package kr.hs.entrydsm.repo.domain.company.persistence.repository

import kr.hs.entrydsm.repo.domain.company.persistence.StandbyCompany
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StandbyCompanyRepository : CrudRepository<StandbyCompany, UUID> {

    fun findByNameContaining(name: String): List<StandbyCompany>
}
