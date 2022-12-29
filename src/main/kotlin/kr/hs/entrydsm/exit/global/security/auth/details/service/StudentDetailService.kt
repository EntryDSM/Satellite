package kr.hs.entrydsm.exit.global.security.auth.details.service

import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.global.security.auth.details.StudentDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentDetailService (
     private val studentRepository: StudentRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val student = studentRepository.findByIdOrNull(UUID.fromString(username)) ?: throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidTokenException

        return StudentDetails(student)
    }
}


