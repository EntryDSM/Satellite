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

    private fun createUpdatedStudent(student: Student, request: StudentSignUpRequest): Student {
        return Student(
            student.email,
            request.name,
            request.grade,
            request.classNum,
            request.number,
            request.profileImagePath
        )
    }

}
