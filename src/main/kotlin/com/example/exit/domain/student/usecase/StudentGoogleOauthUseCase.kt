package com.example.exit.domain.student.usecase

import com.example.exit.domain.auth.Authority
import com.example.exit.domain.auth.dto.response.GoogleLoginLinkResponse
import com.example.exit.domain.auth.dto.response.TokenResponse
import com.example.exit.domain.auth.oauth.GoogleAuth
import com.example.exit.domain.auth.oauth.GoogleEmail
import com.example.exit.domain.auth.properties.GoogleOauthProperties
import com.example.exit.domain.common.exception.EmailSuffixNotValidException
import com.example.exit.domain.student.persistence.Student
import com.example.exit.domain.student.persistence.repository.StudentRepository
import com.example.exit.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service


@Service
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