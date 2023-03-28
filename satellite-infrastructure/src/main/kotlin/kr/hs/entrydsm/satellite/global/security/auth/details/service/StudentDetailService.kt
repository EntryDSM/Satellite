package kr.hs.entrydsm.satellite.global.security.auth.details.service

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.common.exception.jwt.InvalidTokenException
import kr.hs.entrydsm.satellite.common.security.auth.details.StudentDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class StudentDetailService(
    private val studentRepository: StudentRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val student = studentRepository.findByIdOrNull(UUID.fromString(username)) ?: throw InvalidTokenException
        return StudentDetails(student)
    }
}