package kr.hs.entrydsm.repo.domain.student.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.repo.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.repo.global.thirdparty.oauth.GoogleAuth
import kr.hs.entrydsm.repo.global.thirdparty.oauth.GoogleEmail
import kr.hs.entrydsm.repo.global.thirdparty.oauth.properties.GoogleOauthProperties
import kr.hs.entrydsm.repo.global.util.RegexUtil
import java.util.regex.Pattern


@UseCase
class StudentGoogleOauthUseCase(
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

    fun getLink(): kr.hs.entrydsm.repo.domain.auth.dto.response.GoogleLoginLinkResponse {
        return kr.hs.entrydsm.repo.domain.auth.dto.response.GoogleLoginLinkResponse(
            url.format(
                googleProperties.baseUrl,
                googleProperties.clientId,
                googleProperties.redirectUrl
            )
        )
    }

    fun signUpOrIn(code: String): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {
        val email: String = getGoogleEmail(getAccessToken(code))
        checkEmailSuffix(email)

        val student = studentRepository.findByEmail(email)
            ?: throw SignUpRequiredRedirection(email)

        return jwtGenerator.generateBothToken(student.id, kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT)
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