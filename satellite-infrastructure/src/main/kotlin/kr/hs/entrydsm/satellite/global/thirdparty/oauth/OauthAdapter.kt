package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.spi.OauthPort
import kr.hs.entrydsm.satellite.global.thirdparty.oauth.properties.GoogleOauthProperties

@Adapter
class OauthAdapter(
    private val googleProperties: GoogleOauthProperties,
    private val googleAuth: GoogleAuth,
    private val googleEmail: GoogleEmail
) : OauthPort {

    companion object {
        private const val googleUrl = "%s" +
                "?client_id=%s" +
                "&redirect_uri=%s" +
                "&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
    }

    override suspend fun getGoogleLoginLink() =
        googleUrl.format(
            googleProperties.baseUrl,
            googleProperties.clientId,
            googleProperties.redirectUrl
        )

    override suspend fun getGoogleEmailByCode(code: String): String {
        val accessToken = getAccessToken(code)
        return googleEmail.getEmail(
            accessToken = accessToken
        ).email
    }

    private suspend fun getAccessToken(code: String): String {
        return googleAuth.queryAccessToken(
            code = code,
            clientId = googleProperties.clientId,
            clientSecret = googleProperties.secretKey,
            redirectUri = googleProperties.redirectUrl
        ).accessToken
    }
}