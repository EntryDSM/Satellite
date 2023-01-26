package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.PhoneNumberVerificationCodeNotFoundException
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import org.springframework.data.repository.findByIdOrNull

@UseCase
class VerifyPhoneNumberUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
    private val properties: VerificationCodeProperties
) {

    fun execute(phoneNumber: String, code: String) {

        val phoneNumberVerificationCode = phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber)
            ?: throw PhoneNumberVerificationCodeNotFoundException

        if (phoneNumberVerificationCode.code != code) {
            throw VerificationCodeMismatchedException
        }

        phoneNumberVerificationCodeRepository.save(
            PhoneNumberVerificationCode(
                phoneNumber = phoneNumberVerificationCode.phoneNumber,
                code = phoneNumberVerificationCode.code,
                isVerified = true,
                countOfSend = phoneNumberVerificationCode.countOfSend,
                timeToLive = properties.timeToLive
            )
        )
    }
}