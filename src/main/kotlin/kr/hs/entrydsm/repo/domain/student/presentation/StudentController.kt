package kr.hs.entrydsm.repo.domain.student.presentation

import javax.validation.Valid
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.QueryDocumentRequest
import kr.hs.entrydsm.repo.domain.student.presentation.dto.request.StudentSignUpRequest
import kr.hs.entrydsm.repo.domain.student.presentation.dto.response.StudentDocumentListResponse
import kr.hs.entrydsm.repo.domain.student.usecase.QueryStudentDocumentListUseCase
import kr.hs.entrydsm.repo.domain.student.usecase.StudentSignUpUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/student")
@RestController
class StudentController(
    private val studentSignUpUseCase: StudentSignUpUseCase,
    private val queryStudentDocumentListUseCase: QueryStudentDocumentListUseCase
) {

    @PostMapping
    fun studentSignUp(@RequestBody @Valid request: StudentSignUpRequest): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {
        return studentSignUpUseCase.execute(request)
    }

    @GetMapping
    fun queryStudentDocumentList(@ModelAttribute @Valid request: QueryDocumentRequest): StudentDocumentListResponse {
        return queryStudentDocumentListUseCase.execute(request)
    }
}