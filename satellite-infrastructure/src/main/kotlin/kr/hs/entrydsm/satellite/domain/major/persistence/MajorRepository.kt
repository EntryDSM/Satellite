package kr.hs.entrydsm.satellite.domain.major.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.*

interface MajorRepository : ReactiveCrudRepository<MajorEntity, UUID> {
    fun findByNameContaining(name: String): Flux<MajorEntity>
}