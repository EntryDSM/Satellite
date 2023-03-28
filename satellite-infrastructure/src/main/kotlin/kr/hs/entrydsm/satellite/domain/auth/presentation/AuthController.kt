package kr.hs.entrydsm.satellite.domain.auth.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.GoogleLoginLinkResponse
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.usecase.GoogleOauthUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val studentAuthService: GoogleOauthUseCase
) {

    @GetMapping("/google/link")
    fun getGoogleClientId(): GoogleLoginLinkResponse {
        return studentAuthService.getLink()
    }

    @GetMapping("/oauth/sign")
    fun oAuthSingIn(@RequestParam("code") code: String): TokenResponse {
        return studentAuthService.oAuthSignIn(code)
    }
}