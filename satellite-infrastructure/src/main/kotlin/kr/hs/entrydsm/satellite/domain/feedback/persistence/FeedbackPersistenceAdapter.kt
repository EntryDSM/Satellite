package kr.hs.entrydsm.satellite.domain.feedback.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@Adapter
class FeedbackPersistenceAdapter(
    private val feedbackRepository: FeedbackRepository
) : FeedbackPort {

    override fun save(feedback: Feedback): FeedbackJpaEntity =
        feedbackRepository.save(FeedbackJpaEntity.of(feedback))

    override fun queryByDocumentId(documentId: UUID) =
        feedbackRepository.findByDocumentId(documentId)

    override fun queryByDocumentIdAndElementId(documentId: UUID, elementId: UUID) =
        feedbackRepository.findByIdOrNull(
            FeedbackId(
                documentId = documentId,
                elementId = elementId
            )
        )

    override fun existsByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID) =
        feedbackRepository.existsById(
            FeedbackId(
                documentId = documentId,
                elementId = elementId
            )
        )

    override fun deleteAll(feedbackList: List<Feedback>) {
        feedbackRepository.deleteAllById(
            feedbackList.map {
                FeedbackId(
                    documentId = it.documentId,
                    elementId = it.elementId
                )
            }
        )
    }

    override fun deleteByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID) {
        feedbackRepository.deleteById(
            FeedbackId(
                documentId = documentId,
                elementId = elementId
            )
        )
    }
}