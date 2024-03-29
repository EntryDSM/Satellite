package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentListResponse
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import java.util.*

@ReadOnlyUseCase
class QuerySharedDocumentUseCase(
    private val documentPort: DocumentPort
) {
    suspend fun execute(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?
    ): DocumentListResponse {

        val documentList = documentPort.queryByWriterInfoAndStatus(
            status = DocumentStatus.SHARED,
            name = name,
            grade = grade,
            classNum = classNum,
            majorId = majorId
        )

        return DocumentListResponse(
            documentList.map {
                DocumentListResponse.DocumentResponse(it)
            }
        )
    }
}