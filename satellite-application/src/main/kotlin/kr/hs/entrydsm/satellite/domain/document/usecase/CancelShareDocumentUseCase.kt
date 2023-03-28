package kr.hs.entrydsm.satellite.domain.document.usecase

import java.util.UUID
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import org.springframework.data.repository.findByIdOrNull

@UseCase
class CancelShareDocumentUseCase(
    private val documentRepository: DocumentRepository
) {
    fun execute(documentId: UUID) {

        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if (document.status != Status.SHARED) {
            throw DocumentIllegalStatusException
        }

        documentRepository.save(
            document.updateStatus(Status.SUBMITTED)
        )
    }
}