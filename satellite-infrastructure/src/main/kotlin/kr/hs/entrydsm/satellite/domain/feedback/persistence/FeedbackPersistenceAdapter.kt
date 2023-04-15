package kr.hs.entrydsm.satellite.domain.feedback.persistence

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

private typealias E = FeedbackEntity

@Adapter
class FeedbackPersistenceAdapter(
    private val feedbackRepository: FeedbackRepository
) : FeedbackPort {

    override suspend fun save(feedback: Feedback) = feedback.also {
        feedbackRepository.save(FeedbackEntity.of(feedback)).awaitSingle()
    }

    override suspend fun queryByDocumentId(documentId: UUID) =
        feedbackRepository.findByDocumentId(documentId).collectList().awaitSingle()

    override suspend fun queryByDocumentIdAndElementId(documentId: UUID, elementId: UUID) =
        feedbackRepository.findById(FeedbackId(elementId, documentId)).awaitSingleOrNull()

    override suspend fun existsByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID) =
        feedbackRepository.existsById(FeedbackId(elementId, documentId)).awaitSingle()

    override suspend fun deleteByDocumentId(documentId: UUID) {
        feedbackRepository.deleteByDocumentId(documentId)
    }

    override suspend fun deleteByDocumentIdAndFeedbackId(documentId: UUID, elementId: UUID) {
        feedbackRepository.deleteById(FeedbackId(elementId, documentId)).awaitSingle()
    }
}
