package kr.hs.entrydsm.satellite.domain.student.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.GoogleLoginLinkResponse
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.usecase.GoogleOauthUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/student/google")
@RestController
class StudentGoogleOauthController(
    private val studentAuthService: GoogleOauthUseCase
) {

    @GetMapping("/link")
    fun getGoogleClientId(): GoogleLoginLinkResponse {
        return studentAuthService.getLink()
    }

    @GetMapping("/sign")
    fun studentSingUpOrIn(@RequestParam("code") code: String): TokenResponse {
        return studentAuthService.oAuthSignIn(code)
    }
}