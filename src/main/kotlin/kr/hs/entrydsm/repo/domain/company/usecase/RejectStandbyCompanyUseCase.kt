package kr.hs.entrydsm.repo.domain.company.usecase

import java.util.UUID
import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.company.exception.StandbyCompanyNotFoundException
import kr.hs.entrydsm.repo.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull

@UseCase
class RejectStandbyCompanyUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository
) {
    fun execute(standByCompanyId: UUID) {
        val standByCompany = standbyCompanyRepository.findByIdOrNull(standByCompanyId)
            ?: throw StandbyCompanyNotFoundException

        standbyCompanyRepository.delete(standByCompany)
    }
}