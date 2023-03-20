package kr.hs.entrydsm.repo.domain.auth.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.global.thirdparty.aws.ses.AwsSESAdapter
import org.apache.commons.lang.RandomStringUtils
import org.springframework.data.repository.findByIdOrNull

@UseCase
class SendMailVerificationCodeUseCase(
    private val verificationCodeRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository,
    private val properties: kr.hs.entrydsm.repo.domain.auth.properties.VerificationCodeProperties,
    private val awsSESAdapter: AwsSESAdapter
) {

    fun execute(email: String) {

        val verificationCode = verificationCodeRepository.findByIdOrNull(email)

        val countOfSend = verificationCode?.checkAndIncreaseCountOfSend(
            limitCountOfSend = properties.limitCountOfSend
        ) ?: 1
        if (verificationCode?.isVerified == true) {
            throw kr.hs.entrydsm.repo.domain.auth.exception.AlreadyVerifiedException
        }

        val code = RandomStringUtils.randomNumeric(properties.codeLength)

        awsSESAdapter.sendMail(
            email = email,
            mailType = kr.hs.entrydsm.repo.domain.auth.constant.MailType.AUTH_CODE,
            params = mapOf(
                "code" to code
            )
        )

        verificationCodeRepository.save(
            kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode(
                id = email,
                code = code,
                timeToLive = properties.timeToLive,
                countOfSend = countOfSend + 1,
                isVerified = false
            )
        )
    }
}