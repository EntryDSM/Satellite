package kr.hs.entrydsm.repo.domain.company.persistence.repository

import kr.hs.entrydsm.repo.domain.company.persistence.Company
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : CrudRepository<Company, UUID> {
    fun findByEmail(email: String): Company?
    fun findByNameContaining(name: String): List<Company>
}