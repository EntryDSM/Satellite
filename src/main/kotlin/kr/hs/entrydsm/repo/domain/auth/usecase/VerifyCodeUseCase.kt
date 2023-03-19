package kr.hs.entrydsm.repo.domain.auth.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import org.springframework.data.repository.findByIdOrNull

@UseCase
class VerifyCodeUseCase(
    private val verificationCodeRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository,
    private val properties: kr.hs.entrydsm.repo.domain.auth.properties.VerificationCodeProperties
) {

    fun execute(key: String, code: String) {

        val verificationCode = verificationCodeRepository.findByIdOrNull(key)
            ?: throw kr.hs.entrydsm.repo.domain.auth.exception.VerificationCodeNotFoundException

        if (verificationCode.code != code) {
            throw kr.hs.entrydsm.repo.domain.auth.exception.VerificationCodeMismatchedException
        }

        verificationCodeRepository.save(
            kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode(
                id = verificationCode.id,
                code = verificationCode.code,
                isVerified = true,
                countOfSend = verificationCode.countOfSend,
                timeToLive = properties.timeToLive
            )
        )
    }
}