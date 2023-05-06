package kr.hs.entrydsm.satellite.domain.feedback.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface FeedbackRepository : ReactiveCrudRepository<FeedbackEntity, FeedbackId> {
    fun findByDocumentId(documentId: UUID): Flux<FeedbackEntity>
    fun findByDocumentIdIn(documentIds: List<UUID>): Flux<FeedbackEntity>
    fun deleteByDocumentId(documentId: UUID): Mono<Void>
    fun deleteByDocumentIdAndElementId(documentId: UUID, elementId: UUID): Mono<Void>
    fun existsByDocumentIdAndElementId(documentId: UUID, elementId: UUID): Mono<Boolean>
}