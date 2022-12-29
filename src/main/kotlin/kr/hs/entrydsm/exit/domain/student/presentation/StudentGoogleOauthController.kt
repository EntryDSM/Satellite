package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.auth.dto.response.GoogleLoginLinkResponse
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.student.usecase.StudentGoogleOauthUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/student/google")
@RestController
class StudentGoogleOauthController(
    private val studentAuthService: StudentGoogleOauthUseCase
) {

    @GetMapping("/link")
    fun getGoogleClientId(): GoogleLoginLinkResponse {
        return studentAuthService.getLink()
    }

    @GetMapping("/redirect")
    fun studentSingUpOrIn(@RequestParam("code") code: String): TokenResponse {         // Post로 변경하기 TODO
        return studentAuthService.signUpOrIn(code)
    }

}