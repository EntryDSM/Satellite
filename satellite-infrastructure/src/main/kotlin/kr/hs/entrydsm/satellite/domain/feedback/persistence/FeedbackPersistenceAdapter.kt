package kr.hs.entrydsm.satellite.domain.feedback.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import java.util.*

@Adapter
class FeedbackPersistenceAdapter(
    private val feedbackRepository: FeedbackRepository,
    private val objectMapper: ObjectMapper
) : FeedbackPort {

    override suspend fun save(feedback: Feedback) = feedback.also {
        feedbackRepository.save(objectMapper.convertValue(feedback)).awaitSingle()
    }

    override suspend fun queryByDocumentId(documentId: UUID): List<FeedbackEntity> =
        feedbackRepository.findByDocumentId(documentId).collectList().awaitSingle()

    override suspend fun queryByDocumentIdAndElementId(documentId: UUID, elementId: UUID) =
        feedbackRepository.findByDocumentIdAndElementId(documentId, elementId).awaitSingleOrNull()

    override suspend fun queryByDocumentIdIn(documentIds: List<UUID>): List<FeedbackEntity> =
        feedbackRepository.findByDocumentIdIn(documentIds).collectList().awaitSingle()

    override suspend fun existsByDocumentIdAndElementId(documentId: UUID, elementId: UUID): Boolean =
        feedbackRepository.existsByDocumentIdAndElementId(documentId, elementId).awaitSingle()

    override suspend fun deleteByDocumentId(documentId: UUID) {
        feedbackRepository.deleteByDocumentId(documentId).awaitSingleOrNull()
    }

    override suspend fun deleteByDocumentIdAndElementId(documentId: UUID,elementId: UUID) {
        feedbackRepository.deleteByDocumentIdAndElementId(documentId, elementId).awaitSingleOrNull()
    }
}
