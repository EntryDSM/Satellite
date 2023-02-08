package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.global.thirdparty.sms.CoolSmsAdapter
import kr.hs.entrydsm.exit.global.util.RandomUtil
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendPhoneVerificationCodeUseCase(
    private val verificationCodeRepository: VerificationCodeRepository,
    private val properties: VerificationCodeProperties,
    private val coolSmsAdapter: CoolSmsAdapter
) {

    fun execute(phoneNumber: String): SendPhoneNumberCodeResponse {

        val verificationCode = verificationCodeRepository.findByIdOrNull(phoneNumber)

        val countOfSend = verificationCode?.checkAndIncreaseCountOfSend(
            limitCountOfSend = properties.limitCountOfSend
        ) ?: 1
        if (verificationCode?.isVerified == true) {
            throw AlreadyVerifiedException
        }

        val code = RandomUtil.randomNumeric(properties.codeLength)

        coolSmsAdapter.sendAuthCode(phoneNumber, code)

        verificationCodeRepository.save(
            VerificationCode(
                id = phoneNumber,
                code = code,
                timeToLive = properties.timeToLive,
                countOfSend = countOfSend,
                isVerified = false
            )
        )

        return SendPhoneNumberCodeResponse(code)
    }
}

