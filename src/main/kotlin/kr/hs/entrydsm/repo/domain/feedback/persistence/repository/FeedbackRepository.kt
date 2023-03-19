package kr.hs.entrydsm.repo.domain.feedback.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.repo.domain.feedback.persistence.FeedbackId
import org.springframework.data.repository.CrudRepository

interface FeedbackRepository : CrudRepository<Feedback, FeedbackId> {

    fun findByDocumentId(documentId: UUID): List<Feedback>
}