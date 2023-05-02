package kr.hs.entrydsm.satellite.global.security.auth.details.service

import kotlinx.coroutines.reactor.mono
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import kr.hs.entrydsm.satellite.global.exception.InvalidTokenException
import kr.hs.entrydsm.satellite.global.security.auth.details.StudentDetails
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Primary
@Component
class StudentDetailService(
    private val studentPort: StudentPort
): ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        val student = studentPort.queryById(UUID.fromString(username)) ?: throw InvalidTokenException
        return@mono StudentDetails(student)
    }
}