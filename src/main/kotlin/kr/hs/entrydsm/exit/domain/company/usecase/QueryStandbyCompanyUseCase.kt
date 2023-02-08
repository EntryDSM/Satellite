package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.exit.domain.company.presentation.dto.response.CompanyListResponse
import kr.hs.entrydsm.exit.domain.company.presentation.dto.response.CompanyListResponse.CompanyResponse

@ReadOnlyUseCase
class QueryStandbyCompanyUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository
) {

    fun execute(request: QueryCompanyRequest): CompanyListResponse {

        val companyList = standbyCompanyRepository.findByNameContaining(request.name)

        return CompanyListResponse(
            companyList.map { CompanyResponse(it) }
        )
    }
}