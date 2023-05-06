package kr.hs.entrydsm.satellite.domain.feedback.spi

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import java.util.*

interface FeedbackPort {
    suspend fun queryByDocumentId(documentId: UUID): List<Feedback>
    suspend fun queryByDocumentIdAndElementId(documentId: UUID, elementId: UUID): Feedback?
    suspend fun queryByDocumentIdIn(documentIds: List<UUID>): List<Feedback>
    suspend fun existsByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID): Boolean
    suspend fun save(feedback: Feedback): Feedback
    suspend fun deleteByDocumentId(documentId: UUID)
    suspend fun deleteByDocumentIdAndElementId(documentId: UUID, elementId: UUID)
}