package kr.hs.entrydsm.repo.domain.auth.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.repo.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.repo.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.repo.domain.auth.exception.TooManySendVerificationException
import kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.repo.domain.auth.properties.VerificationCodeProperties
import kr.hs.entrydsm.repo.global.thirdparty.sms.CoolSmsAdapter
import org.springframework.data.repository.findByIdOrNull

internal class SendPhoneVerificationCodeUseCaseTest : DescribeSpec({

    val verificationCodeRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.VerificationCodeRepository = mockk()
    val properties: kr.hs.entrydsm.repo.domain.auth.properties.VerificationCodeProperties = anyValueObject(
        "limitCountOfSend" to 3,
        "codeLength" to 6
    )
    val coolSmsAdapter: CoolSmsAdapter = mockk()

    val sendPhoneVerificationCodeUseCase = SendPhoneVerificationCodeUseCase(verificationCodeRepository, properties, coolSmsAdapter)

    describe("sendPhoneNumberVerificationCode") {

        val phoneNumber = anyValueObject<String>()

        context("아직 인증코드가 생성되어있지 않은 전화번호를 받으면") {

            val slot = slot<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode>()

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns null
            every { verificationCodeRepository.save(capture(slot)) } returnsArgument 0
            justRun { coolSmsAdapter.sendAuthCode(phoneNumber, any()) }

            it("랜덤 코드를 생성하여 sms로 전송한다.") {

                sendPhoneVerificationCodeUseCase.execute(phoneNumber)

                verify(exactly = 1) { verificationCodeRepository.save(any()) }
                slot.captured.countOfSend shouldBe 1
            }
        }

        val countOfSend = 0
        val verificationCode = anyValueObject<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode>(
            "countOfSend" to countOfSend
        )

        context("인증코드가 생성되어있는 전화번호를 받으면") {

            val slot = slot<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode>()

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns verificationCode
            every { verificationCodeRepository.save(capture(slot)) } returnsArgument 0
            justRun { coolSmsAdapter.sendAuthCode(phoneNumber, any()) }

            it("전송 횟수를 증가시킨 후 새 랜덤 코드를 sms로 전송한다.") {

                sendPhoneVerificationCodeUseCase.execute(phoneNumber)

                verify(exactly = 1) { verificationCodeRepository.save(any()) }
                slot.captured.code shouldNotBe verificationCode.code
                slot.captured.countOfSend shouldBe countOfSend + 1
            }
        }

        val exceededCountOfSend = 3
        val codeExceededCountOfSend = anyValueObject<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode>(
            "countOfSend" to exceededCountOfSend
        )

        context("인증 요청 횟수가 초과한 전화번호를 받으면") {

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns codeExceededCountOfSend

            it("TooManySendVerification 예외를 던진다.") {

                shouldThrow<kr.hs.entrydsm.repo.domain.auth.exception.TooManySendVerificationException> {
                    sendPhoneVerificationCodeUseCase.execute(phoneNumber)
                }
                verify(exactly = 0) { verificationCodeRepository.save(any()) }
            }
        }

        val alreadyVerifiedCode = anyValueObject<kr.hs.entrydsm.repo.domain.auth.persistence.VerificationCode>(
            "isVerified" to true
        )

        context("이미 인증된 전화번호를 받으면") {

            every { verificationCodeRepository.findByIdOrNull(phoneNumber) } returns alreadyVerifiedCode

            it("AlreadyVerified 예외를 던진다.") {

                shouldThrow<kr.hs.entrydsm.repo.domain.auth.exception.AlreadyVerifiedException> {
                    sendPhoneVerificationCodeUseCase.execute(phoneNumber)
                }
                verify(exactly = 0) { verificationCodeRepository.save(any()) }
            }
        }
    }
})
