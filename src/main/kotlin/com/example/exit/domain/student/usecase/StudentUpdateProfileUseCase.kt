package com.example.exit.domain.student.usecase;

import com.example.exit.domain.common.security.SecurityPort
import com.example.exit.domain.common.security.SecurityUtil
import com.example.exit.domain.student.exception.StudentNotFoundException
import com.example.exit.domain.student.persistence.Student
import com.example.exit.domain.student.persistence.repository.StudentRepository
import com.example.exit.domain.student.presentation.dto.request.StudentUpdateRequest;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional

@Service
class StudentUpdateProfileUseCase(
    private val securityUtil: SecurityUtil,
    private val studentRepository: StudentRepository
) {

    @Transactional
    fun execute(request: StudentUpdateRequest) {
        val studentId = securityUtil.getCurrentUserId()

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
