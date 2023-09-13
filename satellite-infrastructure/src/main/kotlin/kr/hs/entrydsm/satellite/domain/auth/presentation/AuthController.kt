package kr.hs.entrydsm.satellite.domain.auth.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.OauthLinkResponse
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.usecase.GoogleOauthUseCase
import kr.hs.entrydsm.satellite.domain.auth.usecase.ReissueTokenUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val studentAuthService: GoogleOauthUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase
) {

    @GetMapping("/google/link")
    suspend fun getGoogleClientId(): OauthLinkResponse {
        return studentAuthService.getGoogleLoginLink()
    }

    @GetMapping("/oauth/token")
    suspend fun oauthSignIn(@RequestParam("code") code: String): TokenResponse {
        return studentAuthService.oauthSignIn(code)
    }

    @PutMapping("/token")
    suspend fun reissue(@RequestHeader("X-Refresh-Token") token: String): TokenResponse {
        return reissueTokenUseCase.execute(token)
    }
}