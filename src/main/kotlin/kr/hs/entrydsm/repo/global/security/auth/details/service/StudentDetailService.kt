package kr.hs.entrydsm.repo.global.security.auth.details.service

import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.exception.jwt.InvalidTokenException
import kr.hs.entrydsm.repo.global.security.auth.details.StudentDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentDetailService (
     private val studentRepository: StudentRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {


        val student = studentRepository.findByIdOrNull(UUID.fromString(username)) ?: throw InvalidTokenException
        return StudentDetails(student)
    }
}


