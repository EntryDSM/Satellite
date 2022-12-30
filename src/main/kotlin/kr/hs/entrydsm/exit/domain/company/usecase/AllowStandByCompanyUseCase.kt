package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.company.exception.StandbyCompanyNotFoundException
import kr.hs.entrydsm.exit.domain.company.persistence.Company
import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AllowStandByCompanyUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository,
    private val companyRepository: CompanyRepository
) {

    fun execute(standByCompanyId: UUID) {

        val standByCompany = standbyCompanyRepository.findByIdOrNull(standByCompanyId)
            ?: throw StandbyCompanyNotFoundException

        companyRepository.save(
            Company(
                name = standByCompany.name,
                email = standByCompany.email,
                password = standByCompany.password,
                managerName = standByCompany.managerName,
                managerNumber = standByCompany.managerNumber,
                location = standByCompany.location
            )
        )

        standbyCompanyRepository.delete(standByCompany)
    }
}