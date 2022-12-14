package com.example.exit.domain.company.usecase

import com.example.exit.domain.company.exception.StandbyCompanyNotFoundException
import com.example.exit.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RejectStandByCompanyUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository
) {

    fun execute(standByCompanyId: UUID) {
        val standByCompany = standbyCompanyRepository.findByIdOrNull(standByCompanyId)
            ?: throw StandbyCompanyNotFoundException

        standbyCompanyRepository.delete(standByCompany)
    }
}