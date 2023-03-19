package kr.hs.entrydsm.repo.domain.company.usecase

import kr.hs.entrydsm.repo.domain.auth.exception.NotVerifiedException
import kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.company.persistence.StandbyCompany
import kr.hs.entrydsm.repo.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignUpRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class CompanySignUpUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository,
    private val verificationCodeRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun execute(request: CompanySignUpRequest) {

        val phoneNumberVerificationCode = verificationCodeRepository.findByIdOrNull(request.phoneNumber)

        if (phoneNumberVerificationCode == null || !phoneNumberVerificationCode.isVerified) {
            throw kr.hs.entrydsm.repo.domain.auth.exception.NotVerifiedException
        }

        verificationCodeRepository.delete(phoneNumberVerificationCode)

        standbyCompanyRepository.save(
            StandbyCompany(
                name = request.name,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                managerName = request.managerName,
                managerNumber = request.phoneNumber,
                location = request.location
            )
        )
    }
}