package kr.hs.entrydsm.satellite.domain.student.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface StudentRepository : ReactiveCrudRepository<StudentEntity, UUID> {
    fun findByEmail(email: String): Mono<StudentEntity>
    fun existsByEmail(email: String): Mono<Boolean>
}