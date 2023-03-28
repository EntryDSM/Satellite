package kr.hs.entrydsm.satellite.global.thirdparty.oauth

import kr.hs.entrydsm.satellite.common.thirdparty.oauth.dto.response.GoogleEmailResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleUserInfoFeign", url = "https://www.googleapis.com/oauth2/v1/userinfo")
interface GoogleEmail {

    @GetMapping
    fun getEmail(
        @RequestParam("access_token") accessToken: String,
        @RequestParam("alt") alt: String = "json"
    ): GoogleEmailResponse
}