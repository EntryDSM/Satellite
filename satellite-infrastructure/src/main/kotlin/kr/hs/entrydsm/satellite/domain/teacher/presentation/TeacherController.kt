package kr.hs.entrydsm.satellite.domain.teacher.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.satellite.domain.teacher.usecase.TeacherLoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/teacher")
@RestController
class TeacherController(
    private val teacherLoginUseCase: TeacherLoginUseCase
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    suspend fun teacherLogin(@RequestBody request: TeacherSignInRequest): TokenResponse? {
        return teacherLoginUseCase.execute(
            accountId = request.accountId!!,
            password = request.password!!
        )
    }
}