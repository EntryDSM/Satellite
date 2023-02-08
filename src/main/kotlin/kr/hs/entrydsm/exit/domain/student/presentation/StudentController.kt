package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.QueryDocumentRequest
import kr.hs.entrydsm.exit.domain.student.presentation.dto.request.StudentSignUpRequest
import kr.hs.entrydsm.exit.domain.student.presentation.dto.response.StudentDocumentListResponse
import kr.hs.entrydsm.exit.domain.student.usecase.QueryStudentDocumentListUseCase
import kr.hs.entrydsm.exit.domain.student.usecase.StudentSignUpUseCase
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/student")
@RestController
class StudentController(
    private val studentSignUpUseCase: StudentSignUpUseCase,
    private val queryStudentDocumentListUseCase: QueryStudentDocumentListUseCase
) {

    @PostMapping("/")
    fun studentSignUp(@RequestBody @Valid request: StudentSignUpRequest): TokenResponse {
        return studentSignUpUseCase.execute(request)
    }

    @GetMapping("/")
    fun queryStudentDocumentList(@ModelAttribute @Valid request: QueryDocumentRequest): StudentDocumentListResponse {
        return queryStudentDocumentListUseCase.execute(request)
    }
}