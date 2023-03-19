package kr.hs.entrydsm.repo.domain.auth.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.global.thirdparty.sms.CoolSmsAdapter
import kr.hs.entrydsm.repo.global.util.RandomUtil
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendPhoneVerificationCodeUseCase(
    private val verificationCodeRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository,
    private val properties: kr.hs.entrydsm.repo.domain.auth.properties.VerificationCodeProperties,
    private val coolSmsAdapter: CoolSmsAdapter
) {

    fun execute(phoneNumber: String): kr.hs.entrydsm.repo.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse {

        val verificationCode = verificationCodeRepository.findByIdOrNull(phoneNumber)

        val countOfSend = verificationCode?.checkAndIncreaseCountOfSend(
            limitCountOfSend = properties.limitCountOfSend
        ) ?: 1
        if (verificationCode?.isVerified == true) {
            throw kr.hs.entrydsm.repo.domain.auth.exception.AlreadyVerifiedException
        }

        val code = RandomUtil.randomNumeric(properties.codeLength)

        coolSmsAdapter.sendAuthCode(phoneNumber, code)

        verificationCodeRepository.save(
            kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode(
                id = phoneNumber,
                code = code,
                timeToLive = properties.timeToLive,
                countOfSend = countOfSend,
                isVerified = false
            )
        )

        return kr.hs.entrydsm.repo.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse(code)
    }
}

