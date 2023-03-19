package kr.hs.entrydsm.repo.global.security.auth.details.service

import java.util.UUID
import kr.hs.entrydsm.repo.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.repo.global.exception.jwt.InvalidTokenException
import kr.hs.entrydsm.repo.global.security.auth.details.TeacherDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class TeacherDetailService(
    private val teacherRepository: TeacherRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        val teacher = teacherRepository.findByIdOrNull(UUID.fromString(username)) ?: throw InvalidTokenException
        return TeacherDetails(teacher)
    }
}