package kr.hs.entrydsm.exit.domain.feedback.persistence.repository

import kr.hs.entrydsm.exit.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.exit.domain.feedback.persistence.FeedbackId
import org.springframework.data.repository.CrudRepository
import java.util.*

interface FeedbackRepository : CrudRepository<Feedback, FeedbackId> {

    fun findByDocumentId(documentId: UUID): List<Feedback>
}