package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kr.hs.entrydsm.satellite.common.thirdparty.oauth.dto.response.GoogleAccessTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleFeignClient", url = "https://oauth2.googleapis.com/token")
interface GoogleAuth {

    @PostMapping(headers = ["Content-Type: application/x-www-form-urlencoded"])
    fun queryAccessToken(
        @RequestParam("code") code: String,
        @RequestParam("clientId") clientId: String,
        @RequestParam("clientSecret") clientSecret: String,
        @RequestParam("redirectUri") redirectUri: String,
        @RequestParam("grantType") grantType: String = "authorization_code",
    ): GoogleAccessTokenResponse
}