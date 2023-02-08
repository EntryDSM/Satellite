package kr.hs.entrydsm.exit.domain.company.persistence.repository

import kr.hs.entrydsm.exit.domain.company.persistence.Company
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : CrudRepository<Company, UUID> {

    fun findByNameContaining(name: String): List<Company>
}