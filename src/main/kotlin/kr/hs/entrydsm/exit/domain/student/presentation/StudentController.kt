package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.student.presentation.dto.request.StudentUpdateRequest
import kr.hs.entrydsm.exit.domain.student.usecase.StudentUpdateProfileUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/student")
@RestController
class StudentController(
    private val updateProfileUseCase: StudentUpdateProfileUseCase
) {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/profile")
    fun updateProfile(@RequestBody request: StudentUpdateRequest) {
        updateProfileUseCase.execute(request)
    }
}