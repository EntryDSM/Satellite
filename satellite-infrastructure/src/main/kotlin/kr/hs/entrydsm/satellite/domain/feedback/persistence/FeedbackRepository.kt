package kr.hs.entrydsm.satellite.domain.feedback.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.UUID

interface FeedbackRepository : ReactiveCrudRepository<FeedbackEntity, FeedbackId> {
    fun findByDocumentId(documentId: UUID): Flux<FeedbackEntity>
    fun deleteByDocumentId(documentId: UUID)
}