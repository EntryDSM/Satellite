package kr.hs.entrydsm.satellite.domain.student.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.usecase.GoogleOauthUseCase
import kr.hs.entrydsm.satellite.domain.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.domain.student.persistence.Student
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.common.security.jwt.JwtGenerator
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.GoogleAuth
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.GoogleEmail
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.dto.response.GoogleAccessTokenResponse
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.dto.response.GoogleEmailResponse
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.properties.GoogleOauthProperties
import java.time.LocalDateTime

internal class StudentGoogleOauthUseCaseTest : DescribeSpec({

    val studentRepository = mockk<StudentRepository>()
    val googleProperties = anyValueObject<GoogleOauthProperties>()
    val googleAuth = mockk<GoogleAuth>()
    val googleEmail = mockk<GoogleEmail>()
    val jwtGenerator = mockk<JwtGenerator>()

    val googleOauthUseCase = GoogleOauthUseCase(
        studentRepository,
        googleProperties,
        googleAuth,
        googleEmail,
        jwtGenerator
    )

    describe("studentGoogleOauth") {

        val code = "code"
        val googleAccessToken = "google_access_token"
        val email = "email@dsm.hs.kr"
        val student = anyValueObject<Student>(
            "email" to email
        )

        val tokenResponse = TokenResponse(
            accessToken = "access_token",
            accessExpiredAt = LocalDateTime.MAX,
            refreshToken = "refresh_token",
            refreshExpiredAt = LocalDateTime.MAX
        )

        context("가입된 유저의 Oauth 코드가 주어지면") {

            every { googleAuth.queryAccessToken(code, any(), any(), any(), any()) } returns GoogleAccessTokenResponse(
                googleAccessToken
            )
            every { googleEmail.getEmail(googleAccessToken, any()) } returns GoogleEmailResponse(email)
            every { studentRepository.findByEmail(email) } returns student
            every { jwtGenerator.generateBothToken(student.id, Authority.STUDENT) } returns tokenResponse

            it("토큰을 반환한다.") {
                val response = googleOauthUseCase.oAuthSignIn(code)
                response shouldBe tokenResponse
            }
        }

        context("가입되지 않은 유저의 Oauth 코드가 주어지면") {

            every { googleAuth.queryAccessToken(code, any(), any(), any(), any()) } returns GoogleAccessTokenResponse(
                googleAccessToken
            )
            every { googleEmail.getEmail(googleAccessToken, any()) } returns GoogleEmailResponse(email)
            every { studentRepository.findByEmail(email) } returns null

            it("SignUpRequiredRedirection를 던진다.") {
                shouldThrow<SignUpRequiredRedirection> {
                    googleOauthUseCase.oAuthSignIn(code)
                }
            }
        }

        context("학교 이메일이 아닌 Oauth 코드가 주어지면") {

            val gmail = "email@gmail.com"

            every { googleAuth.queryAccessToken(code, any(), any(), any(), any()) } returns GoogleAccessTokenResponse(
                googleAccessToken
            )
            every { googleEmail.getEmail(googleAccessToken, any()) } returns GoogleEmailResponse(gmail)

            it("EmailSuffixNotValid 예외를 던진다.") {
                shouldThrow<EmailSuffixNotValidException> {
                    googleOauthUseCase.oAuthSignIn(code)
                }
            }
        }
    }
})