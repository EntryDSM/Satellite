package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.PhoneNumberVerificationCodeNotFoundException
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.properties.PhoneNumberVerificationCodeProperties
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import org.springframework.data.repository.findByIdOrNull

@UseCase
class ReceivePhoneNumberVerificationCodeUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
    private val properties: PhoneNumberVerificationCodeProperties
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
                timeToLive = properties.baseTimeToLive
            )
        )
    }
}