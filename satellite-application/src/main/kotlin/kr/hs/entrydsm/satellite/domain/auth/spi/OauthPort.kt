package kr.hs.entrydsm.satellite.domain.auth.spi

interface OauthPort {
    suspend fun getGoogleLoginLink(): String
    suspend fun getGoogleEmailByCode(code: String): String
}