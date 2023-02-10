package kr.hs.entrydsm.exit.domain.company.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.domain.auth.exception.NotVerifiedException
import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.VerificationCodeRepository
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanySignUpRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

internal class CompanySignUpUseCaseTest  : DescribeSpec({

    val standByCompanyRepository = mockk<StandbyCompanyRepository>()
    val verificationCodeRepository = mockk<VerificationCodeRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()

    val companySignUpUseCase = CompanySignUpUseCase(standByCompanyRepository, verificationCodeRepository, passwordEncoder)

    describe("companySignUp") {

        val verificationCode =
            anyValueObject<VerificationCode>(
                "isVerified" to true
            )

        val request = anyValueObject<CompanySignUpRequest>()

        context("회사 정보가 주어지면") {

            every { verificationCodeRepository.findByIdOrNull(request.phoneNumber) } returns verificationCode
            justRun { verificationCodeRepository.delete(verificationCode) }
            every { standByCompanyRepository.save(any()) } returnsArgument 0
            every { passwordEncoder.encode(request.password) } returns ""

            it("승인 대기 회사로 저장한다.") {

                companySignUpUseCase.execute(request)
                verify(exactly = 1) { verificationCodeRepository.delete(verificationCode) }
                verify(exactly = 1) { standByCompanyRepository.save(any()) }
            }
        }

        context("전화번호가 null인 회사 정보가 주어지면") {

            every { verificationCodeRepository.findByIdOrNull(request.phoneNumber) } returns null

            it("NotVerified 예외를 던진다.") {

                shouldThrow<NotVerifiedException> {
                    companySignUpUseCase.execute(request)
                }
                verify(exactly = 0) { verificationCodeRepository.delete(verificationCode) }
                verify(exactly = 0) { standByCompanyRepository.save(any()) }
            }
        }

        val notVerifiedCode = anyValueObject<VerificationCode>(
            "isVerified" to false
        )

        context("전화번호가 인증되지 않은 회사 정보가 주어지면") {

            every { verificationCodeRepository.findByIdOrNull(request.phoneNumber) } returns notVerifiedCode

            it("NotVerified 예외를 던진다.") {

                shouldThrow<NotVerifiedException> {
                    companySignUpUseCase.execute(request)
                }
                verify(exactly = 0) { verificationCodeRepository.delete(verificationCode) }
                verify(exactly = 0) { standByCompanyRepository.save(any()) }
            }
        }
    }
})