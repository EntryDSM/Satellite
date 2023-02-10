package kr.hs.entrydsm.exit.domain.auth.usecase

import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeNotFoundException
import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import org.springframework.data.repository.findByIdOrNull

@UseCase
class VerifyCodeUseCase(
    private val verificationCodeRepository: VerificationCodeRepository,
    private val properties: VerificationCodeProperties
) {

    fun execute(key: String, code: String) {

        val verificationCode = verificationCodeRepository.findByIdOrNull(key)
            ?: throw VerificationCodeNotFoundException

        if (verificationCode.code != code) {
            throw VerificationCodeMismatchedException
        }

        verificationCodeRepository.save(
            VerificationCode(
                id = verificationCode.id,
                code = verificationCode.code,
                isVerified = true,
                countOfSend = verificationCode.countOfSend,
                timeToLive = properties.timeToLive
            )
        )
    }
}