package kr.hs.entrydsm.repo.domain.feedback.persistence.repository

import kr.hs.entrydsm.repo.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.repo.domain.feedback.persistence.FeedbackId
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface FeedbackRepository : CrudRepository<Feedback, FeedbackId> {

    fun findByDocumentId(documentId: UUID): List<Feedback>
}