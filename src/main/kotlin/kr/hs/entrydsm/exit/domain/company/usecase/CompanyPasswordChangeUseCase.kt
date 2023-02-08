package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.NotVerifiedException
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.company.exception.CompanyNotFoundException
import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanyPasswordChangeRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class CompanyPasswordChangeUseCase(
    private val companyRepository: CompanyRepository,
    private val verificationCodeRepository: VerificationCodeRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun execute(request: CompanyPasswordChangeRequest) {

        var company = companyRepository.findByEmail(request.email) ?: throw CompanyNotFoundException

        val verificationCode = verificationCodeRepository.findByIdOrNull(company.email)
        if (verificationCode?.isVerified == false) {
            throw NotVerifiedException
        }

        company = company.changePassword(
            password = passwordEncoder.encode(request.password)
        )

        if (!company.isInitialized) {
            company = company.initialize()
        }

        companyRepository.save(company)
    }
}