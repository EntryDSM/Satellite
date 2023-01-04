package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.company.exception.StandbyCompanyNotFoundException
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

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


