package com.example.exit.global.security.auth.details.service

import com.example.exit.domain.teacher.persistence.repository.TeacherRepository
import com.example.exit.global.security.auth.details.TeacherDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeacherDetailService (
     private val teacherRepository: TeacherRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val teacher = teacherRepository.findByIdOrNull(UUID.fromString(username)) ?: throw Exception() //TODO

        return TeacherDetails(teacher.id);
    }
}