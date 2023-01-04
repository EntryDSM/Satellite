package kr.hs.entrydsm.exit.domain.student.usecase

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.domain.auth.dto.response.GoogleLoginLinkResponse
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.auth.oauth.GoogleAuth
import kr.hs.entrydsm.exit.domain.auth.oauth.GoogleEmail
import kr.hs.entrydsm.exit.domain.auth.properties.GoogleOauthProperties
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service


@UseCase
class StudentGoogleOauthUseCase(
    private val studentRepository: StudentRepository,
    private val googleProperties: GoogleOauthProperties,
    private val googleAuth: GoogleAuth,
    private val googleEmail: GoogleEmail,
    private val tokenProvider: JwtTokenProvider
) {

    companion object {
        private const val url = "%s?client_id=%s&redirect_uri=%s&response_type=code" +
                "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
        private const val schoolEmail = "@dsm.hs.kr"
        private const val schoolEmailLength = 10
    }

    fun getLink(): GoogleLoginLinkResponse {
        return GoogleLoginLinkResponse(
            url.format(
                googleProperties.baseUrl,
                googleProperties.clientId,
                googleProperties.redirectUrl
            )
        )
    }

    fun signUpOrIn(code: String): TokenResponse {
        val email: String = getGoogleEmail(getAccessToken(code))
        val emailSuffix: String = email.substring(email.length - schoolEmailLength)

        if (emailSuffix != schoolEmail) {
            throw EmailSuffixNotValidException
        }

        val student = studentRepository.findByEmail(email)
            ?: studentSignup(email)

        return tokenProvider.generateBothToken(student.id, Authority.STUDENT)
    }

    private fun studentSignup(email: String): Student {
        return studentRepository.save(Student(email = email))
    }

    private fun getAccessToken(code: String): String {
        return googleAuth.queryAccessToken(
            code,
            googleProperties.clientId,
            googleProperties.secretKey,
            googleProperties.redirectUrl
        ).accessToken
    }

    private fun getGoogleEmail(accessToken: String): String {
        return googleEmail.getEmail(
            accessToken
        ).email
    }
}