package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.PhoneNumberVerificationCodeNotFoundException
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReceivePhoneNumberVerificationCodeUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
) {

    companion object{
        private const val MAX_TIME_TO_LIVE = 999999L
    }

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
                timeToLive = MAX_TIME_TO_LIVE
            )
        )
    }
}