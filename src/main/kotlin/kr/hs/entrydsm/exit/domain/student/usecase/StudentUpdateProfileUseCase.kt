package kr.hs.entrydsm.exit.domain.student.usecase;

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.student.exception.StudentNotFoundException
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.domain.student.presentation.dto.request.StudentUpdateRequest;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional

@UseCase
class StudentUpdateProfileUseCase(
    private val studentRepository: StudentRepository
) {

    @Transactional
    fun execute(request: StudentUpdateRequest) {
        val studentId = SecurityUtil.getCurrentUserId()

        val student = studentRepository.findById(studentId)
            .orElseThrow { throw StudentNotFoundException }

        val updatedStudent = createUpdatedStudent(student, request)

        studentRepository.save(updatedStudent)
    }

    private fun createUpdatedStudent(student: Student, request: StudentUpdateRequest): Student {
        return Student(
            student.id,
            student.email,
            request.name,
            request.grade,
            request.classNum,
            request.number,
            request.major,
            request.profileImagePath
        )
    }

}
