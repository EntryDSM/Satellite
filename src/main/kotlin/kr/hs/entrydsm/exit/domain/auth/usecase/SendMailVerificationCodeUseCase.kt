package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.constant.MailType
import kr.hs.entrydsm.exit.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.global.thirdparty.aws.ses.AwsSESAdapter
import kr.hs.entrydsm.exit.global.util.RandomUtil
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendMailVerificationCodeUseCase(
    private val verificationCodeRepository: VerificationCodeRepository,
    private val properties: VerificationCodeProperties,
    private val awsSESAdapter: AwsSESAdapter
) {

    fun execute(email: String) {

        val verificationCode = verificationCodeRepository.findByIdOrNull(email)

        val countOfSend = verificationCode?.checkAndIncreaseCountOfSend(
            limitCountOfSend = properties.limitCountOfSend
        ) ?: 1
        if (verificationCode?.isVerified == true) {
            throw AlreadyVerifiedException
        }

        val code = RandomUtil.randomNumeric(properties.codeLength)

        awsSESAdapter.sendMail(
            email = email,
            mailType = MailType.AUTH_CODE,
            params = mapOf(
                "code" to code
            )
        )

        verificationCodeRepository.save(
            VerificationCode(
                id = email,
                code = code,
                timeToLive = properties.timeToLive,
                countOfSend = countOfSend + 1,
                isVerified = false
            )
        )
    }
}