package kr.hs.entrydsm.satellite.domain.auth.usecase

import kr.hs.entrydsm.satellite.domain.auth.dto.response.GoogleLoginLinkResponse
import kr.hs.entrydsm.satellite.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.satellite.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.satellite.common.security.jwt.JwtGenerator
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.GoogleAuth
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.GoogleEmail
import kr.hs.entrydsm.satellite.common.thirdparty.oauth.properties.GoogleOauthProperties
import kr.hs.entrydsm.satellite.common.util.RegexUtil
import java.util.regex.Pattern

@UseCase
class GoogleOauthUseCase(
    private val studentRepository: StudentRepository,
    private val googleProperties: GoogleOauthProperties,
    private val googleAuth: GoogleAuth,
    private val googleEmail: GoogleEmail,
    private val jwtGenerator: JwtGenerator
) {
    companion object {
        private const val url = "%s?client_id=%s&redirect_uri=%s&response_type=code" +
            "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
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

    fun oAuthSignIn(code: String): TokenResponse {
        val email: String = getGoogleEmail(getAccessToken(code))
        checkEmailSuffix(email)

        val student = studentRepository.findByEmail(email)
            ?: throw SignUpRequiredRedirection(email)

        return jwtGenerator.generateBothToken(student.id, Authority.STUDENT)
    }

    private fun checkEmailSuffix(email: String) {
        if (!Pattern.matches(RegexUtil.EMAIL_EXP, email)) {
            throw EmailSuffixNotValidException
        }
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