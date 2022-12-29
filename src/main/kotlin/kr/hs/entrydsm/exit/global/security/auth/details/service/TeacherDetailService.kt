package kr.hs.entrydsm.exit.global.security.auth.details.service

import kr.hs.entrydsm.exit.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.exit.global.security.auth.details.TeacherDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherDetailService (
     private val teacherRepository: TeacherRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val teacher = teacherRepository.findByIdOrNull(UUID.fromString(username)) ?: throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidTokenException

        return TeacherDetails(teacher)
    }
}