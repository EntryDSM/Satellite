package kr.hs.entrydsm.satellite.domain.student.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.OauthPort
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.domain.auth.usecase.GoogleOauthUseCase
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain
import kr.hs.entrydsm.satellite.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.time.LocalDateTime

internal class StudentGoogleOauthUseCaseTest : DescribeSpec({

    val studentPort = mockk<StudentPort>()
    val oauthPort = mockk<OauthPort>()
    val tokenPort = mockk<TokenPort>()

    val googleOauthUseCase = GoogleOauthUseCase(studentPort, oauthPort, tokenPort)

    describe("studentGoogleOauth") {

        val code = "code"
        val email = "email@dsm.hs.kr"
        val student = anyValueObject<StudentDomain>(
            "email" to email
        )

        val tokenResponse = TokenResponse(
            accessToken = "access_token",
            accessExpiredAt = LocalDateTime.MAX,
            refreshToken = "refresh_token",
            refreshExpiredAt = LocalDateTime.MAX
        )

        context("가입된 유저의 Oauth 코드가 주어지면") {

            coEvery { oauthPort.getGoogleEmailByCode(code) } returns email
            coEvery { studentPort.queryByEmail(email) } returns student
            coEvery { tokenPort.generateBothToken(student.id, Authority.STUDENT) } returns tokenResponse

            it("토큰을 반환한다.") {
                val response = googleOauthUseCase.oauthSignIn(code)
                response shouldBe tokenResponse
            }
        }

        context("가입되지 않은 유저의 Oauth 코드가 주어지면") {

            coEvery { oauthPort.getGoogleEmailByCode(code) } returns email
            coEvery { studentPort.queryByEmail(email) } returns null

            it("SignUpRequiredRedirection를 던진다.") {
                shouldThrow<SignUpRequiredRedirection> {
                    googleOauthUseCase.oauthSignIn(code)
                }
            }
        }

        context("학교 이메일이 아닌 Oauth 코드가 주어지면") {

            val gmail = "email@gmail.com"

            coEvery { oauthPort.getGoogleEmailByCode(code) } returns gmail

            it("EmailSuffixNotValid 예외를 던진다.") {
                shouldThrow<EmailSuffixNotValidException> {
                    googleOauthUseCase.oauthSignIn(code)
                }
            }
        }
    }
})