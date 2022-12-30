package kr.hs.entrydsm.exit.domain.teacher.presentation

import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.exit.domain.teacher.usecase.TeacherLoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/teacher")
@RestController
class TeacherController(
    private val teacherLoginUseCase: TeacherLoginUseCase
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    fun teacherLogin(@RequestBody request: TeacherSignInRequest): TokenResponse? {
        return teacherLoginUseCase.execute(request)
    }
}