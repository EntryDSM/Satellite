package kr.hs.entrydsm.exit.domain.auth.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.domain.auth.exception.AlreadyVerifiedException
import kr.hs.entrydsm.exit.domain.auth.exception.TooManySendVerificationException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.properties.PhoneNumberVerificationCodeProperties
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import kr.hs.entrydsm.exit.global.util.GenerateRandomCodeUtil
import org.springframework.data.repository.findByIdOrNull

internal class SendPhoneNumberVerificationCodeUseCaseTest : DescribeSpec({

    val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository = mockk()
    val properties: PhoneNumberVerificationCodeProperties = anyValueObject(
        "limitCountOfSend" to 3,
        "codeLength" to 6
    )
    mockkObject(GenerateRandomCodeUtil)

    val sendPhoneNumberVerificationCodeUseCase = SendPhoneNumberVerificationCodeUseCase(phoneNumberVerificationCodeRepository, properties)

    describe("sendPhoneNumberVerificationCode") {

        val phoneNumber = anyValueObject<String>()

        context("아직 인증코드가 생성되어있지 않은 전화번호를 받으면") {

            val slot = slot<PhoneNumberVerificationCode>()

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns null
            every { phoneNumberVerificationCodeRepository.save(capture(slot)) } returnsArgument 0

            it("랜덤 코드를 생성하여 sms로 전송한다.") {

                sendPhoneNumberVerificationCodeUseCase.execute(phoneNumber)

                verify(exactly = 1) { phoneNumberVerificationCodeRepository.save(any()) }
                slot.captured.countOfSend shouldBe 1
            }
        }

        val countOfSend = 0
        val phoneNumberVerificationCode = anyValueObject<PhoneNumberVerificationCode>(
            "countOfSend" to countOfSend
        )

        context("인증코드가 생성되어있는 전화번호를 받으면") {

            val slot = slot<PhoneNumberVerificationCode>()

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns phoneNumberVerificationCode
            every { phoneNumberVerificationCodeRepository.save(capture(slot)) } returnsArgument 0

            it("전송 횟수를 증가시킨 후 새 랜덤 코드를 sms로 전송한다.") {

                sendPhoneNumberVerificationCodeUseCase.execute(phoneNumber)

                verify(exactly = 1) { phoneNumberVerificationCodeRepository.save(any()) }
                slot.captured.code shouldNotBe phoneNumberVerificationCode.code
                slot.captured.countOfSend shouldBe countOfSend + 1
            }
        }

        val exceededCountOfSend = 3
        val codeExceededCountOfSend = anyValueObject<PhoneNumberVerificationCode>(
            "countOfSend" to exceededCountOfSend
        )

        context("인증 요청 횟수가 초과한 전화번호를 받으면") {

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns codeExceededCountOfSend

            it("TooManySendVerification 예외를 던진다.") {

                shouldThrow<TooManySendVerificationException> {
                    sendPhoneNumberVerificationCodeUseCase.execute(phoneNumber)
                }
                verify(exactly = 0) { phoneNumberVerificationCodeRepository.save(any()) }
            }
        }

        val alreadyVerifiedCode = anyValueObject<PhoneNumberVerificationCode>(
            "isVerified" to true
        )

        context("이미 인증된 전화번호를 받으면") {

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns alreadyVerifiedCode

            it("AlreadyVerified 예외를 던진다.") {

                shouldThrow<AlreadyVerifiedException> {
                    sendPhoneNumberVerificationCodeUseCase.execute(phoneNumber)
                }
                verify(exactly = 0) { phoneNumberVerificationCodeRepository.save(any()) }
            }
        }
    }

    afterContainer()
})
