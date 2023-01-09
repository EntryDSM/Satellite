package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.exception.IllegalStatusException
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@UseCase
class ShareDocumentUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(documentId: UUID) {

        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if(document.status != Status.SUBMITTED) {
            throw IllegalStatusException
        }

        documentRepository.save(
            document.updateStatus(Status.SHARED)
        )
    }
}