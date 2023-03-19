package kr.hs.entrydsm.repo.domain.company.presentation.dto.response

import kr.hs.entrydsm.repo.domain.company.persistence.Company
import kr.hs.entrydsm.repo.domain.company.persistence.StandbyCompany

class CompanyListResponse(
    val companyList: List<CompanyResponse>
) {
    data class CompanyResponse(
        val name: String,
        val email: String,
        val managerName: String,
        val managerNumber: String,
        val location: String
    ) {
        constructor(standbyCompany: StandbyCompany) : this(
            name = standbyCompany.name,
            email = standbyCompany.email,
            managerName = standbyCompany.managerName,
            managerNumber = standbyCompany.managerNumber,
            location = standbyCompany.location
        )

        constructor(company: Company) : this(
            name = company.name,
            email = company.email,
            managerName = company.managerName,
            managerNumber = company.managerNumber,
            location = company.location
        )
    }
}