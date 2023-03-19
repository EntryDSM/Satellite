package kr.hs.entrydsm.repo.domain.company.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.company.persistence.StandbyCompany
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StandbyCompanyRepository : CrudRepository<StandbyCompany, UUID> {

    fun findByNameContaining(name: String): List<StandbyCompany>
}