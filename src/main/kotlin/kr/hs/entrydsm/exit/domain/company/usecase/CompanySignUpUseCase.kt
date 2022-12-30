package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.IsNotVerifiedPhoneNumber
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.company.persistence.StandbyCompany
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.SignUpRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CompanySignUpUseCase(
    private val standByCompanyRepository: StandbyCompanyRepository,
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository
) {

    fun execute(request: SignUpRequest) {

        val phoneNumberVerificationCode = phoneNumberVerificationCodeRepository.findByIdOrNull(request.phoneNumber)

        if (phoneNumberVerificationCode == null || !phoneNumberVerificationCode.isVerified) {
            throw IsNotVerifiedPhoneNumber
        } else {
            phoneNumberVerificationCodeRepository.deleteById(request.phoneNumber)
        }

        standByCompanyRepository.save(
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