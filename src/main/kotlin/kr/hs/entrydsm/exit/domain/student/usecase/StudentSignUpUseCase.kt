package kr.hs.entrydsm.exit.domain.student.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.domain.student.presentation.dto.request.StudentSignUpRequest
import kr.hs.entrydsm.exit.global.security.SecurityUtil

@UseCase
class StudentSignUpUseCase(
    private val studentRepository: StudentRepository
) {

    fun execute(request: StudentSignUpRequest) {

        val student = SecurityUtil.getCurrentStudent()

        val updatedStudent = createUpdatedStudent(student, request)

        studentRepository.save(updatedStudent)
    }

    private fun checkEmailSuffix(email: String) {
        if (!Pattern.matches(RegexUtil.EMAIL_EXP, email)) {
            throw EmailSuffixNotValidException
        }
    }

}
