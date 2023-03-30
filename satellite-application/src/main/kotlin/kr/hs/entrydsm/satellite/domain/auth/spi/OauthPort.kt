package kr.hs.entrydsm.satellite.domain.auth.spi

interface OauthPort {
    fun getGoogleLoginLink(): String
    fun getGoogleEmailByCode(code: String): String
}