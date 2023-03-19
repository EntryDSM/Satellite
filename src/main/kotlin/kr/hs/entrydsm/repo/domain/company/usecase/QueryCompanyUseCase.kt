package kr.hs.entrydsm.repo.domain.company.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.response.CompanyListResponse

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