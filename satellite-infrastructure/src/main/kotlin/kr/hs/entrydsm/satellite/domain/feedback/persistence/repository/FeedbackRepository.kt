package kr.hs.entrydsm.satellite.domain.feedback.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.persistence.FeedbackId
import org.springframework.data.repository.CrudRepository

interface FeedbackRepository : CrudRepository<Feedback, FeedbackId> {

    fun findByDocumentId(documentId: UUID): List<Feedback>
}