package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.exit.domain.auth.exception.TooManySendVerificationException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.properties.PhoneNumberVerificationCodeProperties
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.global.util.GenerateRandomCodeUtil
import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendPhoneNumberVerificationCodeUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
    private val properties: PhoneNumberVerificationCodeProperties
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

        val code = GenerateRandomCodeUtil.randomNumber(properties.codeLength)

        phoneNumberVerificationCodeRepository.save(
            PhoneNumberVerificationCode(
                phoneNumber = phoneNumber,
                code = code,
                timeToLive = properties.baseTimeToLive,
                countOfSend = countOfSend + 1,
                isVerified = false
            )
        )

        sendAuthCode(phoneNumber, code)

        return SendPhoneNumberCodeResponse(code)
    }

    companion object{
        private const val TYPE = "SMS"
        private const val APP_VERSION = "app 1.0"
    }
    fun sendAuthCode(phoneNumber: String, authCode: String?) {
        val message = Message(properties.key, properties.secret)

        val params: HashMap<String, String> = HashMap()
        params["to"] = phoneNumber
        params["from"] = properties.phoneNumber
        params["type"] = TYPE
        params["text"] = "인증번호 " + authCode + "를 입력하세요."
        params["app_version"] = APP_VERSION

        try {
            message.send(params)
        } catch (e: CoolsmsException) {
            e.stackTrace
        }
    }
}

