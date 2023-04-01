package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import java.util.*

@UseCase
class CancelShareDocumentUseCase(
    private val documentPort: DocumentPort
) {
    fun execute(documentId: UUID) {

        val document = documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        if (document.status != DocumentStatus.SHARED) {
            throw DocumentIllegalStatusException
        }

        documentPort.save(
            document.copy(status = DocumentStatus.SUBMITTED)
        )
    }
}