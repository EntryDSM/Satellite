package kr.hs.entrydsm.repo.domain.company.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.response.CompanyListResponse
import kr.hs.entrydsm.repo.domain.company.presentation.dto.response.CompanyListResponse.CompanyResponse

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