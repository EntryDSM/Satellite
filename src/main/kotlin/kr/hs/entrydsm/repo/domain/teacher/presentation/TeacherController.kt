package kr.hs.entrydsm.repo.domain.teacher.presentation

import kr.hs.entrydsm.repo.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.repo.domain.teacher.usecase.TeacherLoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/teacher")
@RestController
class TeacherController(
    private val teacherLoginUseCase: TeacherLoginUseCase
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    fun teacherLogin(@RequestBody request: TeacherSignInRequest): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse? {
        return teacherLoginUseCase.execute(request)
    }
}