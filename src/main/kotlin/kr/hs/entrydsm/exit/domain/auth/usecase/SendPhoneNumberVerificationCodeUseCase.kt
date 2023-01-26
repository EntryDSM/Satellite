package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.exit.domain.auth.exception.TooManySendVerificationException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.global.thirdparty.sms.CoolSmsAdapter
import org.apache.commons.lang.RandomStringUtils
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendPhoneNumberVerificationCodeUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
    private val properties: VerificationCodeProperties,
    private val coolSmsAdapter: CoolSmsAdapter
) {

    fun execute(phoneNumber: String): SendPhoneNumberCodeResponse {

        val phoneNumberVerificationCode = phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber)

        val countOfSend = phoneNumberVerificationCode?.countOfSend ?: 0
        if (countOfSend >= properties.limitCountOfSend) {
            throw TooManySendVerificationException
        }

        if (phoneNumberVerificationCode?.isVerified == true) {
            throw AlreadyVerifiedException
        }

        val code = getRandomCode()

        phoneNumberVerificationCodeRepository.save(
            PhoneNumberVerificationCode(
                phoneNumber = phoneNumber,
                code = code,
                timeToLive = properties.timeToLive,
                countOfSend = countOfSend + 1,
                isVerified = false
            )
        )

        coolSmsAdapter.sendAuthCode(phoneNumber, code)

        return SendPhoneNumberCodeResponse(code)
    }

    private fun getRandomCode(): String = RandomStringUtils.randomNumeric(6)
}

