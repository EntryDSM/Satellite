package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.exit.domain.company.presentation.dto.response.CompanyListResponse

@ReadOnlyUseCase
class QueryCompanyUseCase(
    private val companyRepository: CompanyRepository
) {

    fun execute(request: QueryCompanyRequest): CompanyListResponse {

        val companyList = companyRepository.findByNameContaining(request.name)

        return CompanyListResponse(
            companyList.map { CompanyListResponse.CompanyResponse(it) }
        )
    }
}