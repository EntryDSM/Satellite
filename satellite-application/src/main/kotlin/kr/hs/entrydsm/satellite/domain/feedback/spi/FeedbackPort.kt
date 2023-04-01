package kr.hs.entrydsm.satellite.domain.feedback.spi

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import java.util.*

interface FeedbackPort {
    fun queryByDocumentId(documentId: UUID): List<Feedback>
    fun queryByDocumentIdAndElementId(documentId: UUID, elementId: UUID): Feedback?
    fun existsByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID): Boolean
    fun save(feedback: Feedback): Feedback
    fun deleteAll(feedbackList: List<Feedback>)
    fun deleteByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID)
}