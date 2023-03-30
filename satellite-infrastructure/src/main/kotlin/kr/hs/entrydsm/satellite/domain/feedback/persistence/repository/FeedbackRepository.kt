package kr.hs.entrydsm.satellite.domain.feedback.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.feedback.persistence.FeedbackJpaEntity
import kr.hs.entrydsm.satellite.domain.feedback.persistence.FeedbackId
import org.springframework.data.repository.CrudRepository

interface FeedbackRepository : CrudRepository<FeedbackJpaEntity, FeedbackId> {

    fun findByDocumentId(documentId: UUID): List<FeedbackJpaEntity>
}