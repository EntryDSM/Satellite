package kr.hs.entrydsm.satellite.domain.feedback.spi

import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import java.util.UUID

interface FeedbackPort {
    fun queryByDocumentId(documentId: UUID): List<Feedback>
}