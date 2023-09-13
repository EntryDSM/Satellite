package kr.hs.entrydsm.satellite.domain.auth.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.OauthLinkResponse
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.OauthPort
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student.Companion.checkEmailSuffix
import kr.hs.entrydsm.satellite.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class GoogleOauthUseCase(
    private val studentPort: StudentPort,
    private val oauthPort: OauthPort,
    private val tokenPort: TokenPort
) {

    suspend fun getGoogleLoginLink(): OauthLinkResponse {
        return OauthLinkResponse(
            loginLink = oauthPort.getGoogleLoginLink()
        )
    }

    suspend fun oauthSignIn(code: String): TokenResponse {
        val email = oauthPort.getGoogleEmailByCode(code)
        println(email)
        checkEmailSuffix(email)

        val student = studentPort.queryByEmail(email)
            ?: throw SignUpRequiredRedirection(email)
        println(student.name)
        return tokenPort.generateBothToken(student.id, Authority.STUDENT)
    }
}