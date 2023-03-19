package kr.hs.entrydsm.repo.domain.student.presentation

import kr.hs.entrydsm.repo.domain.student.usecase.StudentGoogleOauthUseCase
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
    fun getGoogleClientId(): kr.hs.entrydsm.repo.domain.auth.dto.response.GoogleLoginLinkResponse {
        return studentAuthService.getLink()
    }

    @GetMapping("/sign")
    fun studentSingUpOrIn(@RequestParam("code") code: String): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {
        return studentAuthService.signUpOrIn(code)
    }
}