package com.example.exit.global.security.auth.details.service

import com.example.exit.domain.student.persistence.repository.StudentRepository
import com.example.exit.global.exception.jwt.InvalidTokenException
import com.example.exit.global.security.auth.details.StudentDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentDetailService (
     private val studentRepository: StudentRepository
): UserDetailsService{

    override fun loadUserByUsername(username: String?): UserDetails {
        val student = studentRepository.findByIdOrNull(UUID.fromString(username)) ?: throw InvalidTokenException

        return StudentDetails(student.id);
    }
}