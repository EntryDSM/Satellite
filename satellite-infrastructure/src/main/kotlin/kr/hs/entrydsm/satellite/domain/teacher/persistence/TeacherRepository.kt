package kr.hs.entrydsm.satellite.domain.teacher.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface TeacherRepository : ReactiveCrudRepository<TeacherEntity, UUID> {
    fun findByAccountId(accountId: String): Mono<TeacherEntity>
}