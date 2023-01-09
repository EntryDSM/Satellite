package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.NotVerifiedException
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.company.persistence.StandbyCompany
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanySignUpRequest
import org.springframework.data.repository.findByIdOrNull

@UseCase
class CompanySignUpUseCase(
    private val standbyCompanyRepository: StandbyCompanyRepository,
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository
) {

    fun execute(request: CompanySignUpRequest) {

        val phoneNumberVerificationCode = phoneNumberVerificationCodeRepository.findByIdOrNull(request.phoneNumber)

        if (phoneNumberVerificationCode == null || !phoneNumberVerificationCode.isVerified) {
            throw NotVerifiedException
        }

        phoneNumberVerificationCodeRepository.delete(phoneNumberVerificationCode)

        standbyCompanyRepository.save(
            StandbyCompany(
                name = request.name,
                email = request.email,
                password = request.password,
                managerName = request.managerName,
                managerNumber = request.phoneNumber,
                location = request.location
            )
        )
    }
}