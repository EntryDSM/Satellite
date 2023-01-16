package kr.hs.entrydsm.exit.domain.auth.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.domain.auth.exception.VerificationCodeMismatchedException
import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.properties.PhoneNumberVerificationCodeProperties
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import org.springframework.data.repository.findByIdOrNull

internal class ReceivePhoneNumberVerificationCodeUseCaseTest : DescribeSpec({

    val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository = mockk()
    val properties: PhoneNumberVerificationCodeProperties = anyValueObject()

    val receivePhoneNumberVerificationCodeUseCase = ReceivePhoneNumberVerificationCodeUseCase(phoneNumberVerificationCodeRepository, properties)

    describe("receivePhoneNumberVerificationCode"){

        val phoneNumber = "01012345678"
        val code = "123456"
        val phoneNumberVerificationCode = anyValueObject<PhoneNumberVerificationCode>(
            "code" to code
        )

        context("전화번호와 코드가 주어지면") {

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns phoneNumberVerificationCode
            every { phoneNumberVerificationCodeRepository.save(any()) } returnsArgument 0

            it("저장된 코드를 인증상태로 변경한다.") {

                receivePhoneNumberVerificationCodeUseCase.execute(phoneNumber, code)

                verify(exactly = 1) { phoneNumberVerificationCodeRepository.save(any()) }
            }
        }

        val wrongCode = "234567"

        context("잘못된 코드가 주어지면") {

            every { phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber) } returns phoneNumberVerificationCode
            every { phoneNumberVerificationCodeRepository.save(any()) } returnsArgument 0

            it("VerificationCodeMismatched 예외를 던진다.") {
                shouldThrow<VerificationCodeMismatchedException> {
                    receivePhoneNumberVerificationCodeUseCase.execute(phoneNumber, wrongCode)
                }
                verify(exactly = 0) { phoneNumberVerificationCodeRepository.save(any()) }
            }
        }
    }

    afterContainer()
})
