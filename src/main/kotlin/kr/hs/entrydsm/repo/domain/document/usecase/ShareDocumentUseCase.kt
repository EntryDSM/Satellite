package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.exception.IllegalStatusException
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.feedback.persistence.repository.FeedbackRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@UseCase
class ShareDocumentUseCase(
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {

    fun execute(documentId: UUID) {

        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if(document.status != Status.SUBMITTED) {
            throw IllegalStatusException
        }

        val feedbackList = feedbackRepository.findByDocumentId(document.id)
        feedbackRepository.deleteAll(feedbackList)

        documentRepository.save(
            document.updateStatus(Status.SHARED)
        )
    }
}