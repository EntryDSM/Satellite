package kr.hs.entrydsm.satellite.global.security.auth.details.service

import kotlinx.coroutines.reactor.mono
import kr.hs.entrydsm.satellite.domain.teacher.spi.TeacherPort
import kr.hs.entrydsm.satellite.global.exception.InvalidTokenException
import kr.hs.entrydsm.satellite.global.security.auth.details.TeacherDetails
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class TeacherDetailService(
    private val teacherPort: TeacherPort
): ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails>? = mono {
        val teacher = teacherPort.queryById(UUID.fromString(username)) ?: throw InvalidTokenException
        return@mono TeacherDetails(teacher)
    }
}