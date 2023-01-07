package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.student.presentation.dto.request.StudentSignUpRequest
import kr.hs.entrydsm.exit.domain.student.usecase.StudentSignUpUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/student")
@RestController
class StudentController(
    private val updateProfileUseCase: StudentSignUpUseCase
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/profile")
    fun updateProfile(@RequestBody @Valid request: StudentSignUpRequest) {
        updateProfileUseCase.execute(request)
    }
}