package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.persistence.repository.findByStatusAndWriterInfo
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.QueryDocumentRequest
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentListResponse
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentListResponse.DocumentResponse

@ReadOnlyUseCase
class QuerySharedDocumentUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: QueryDocumentRequest): DocumentListResponse {

        val documentList = request.run {
            documentRepository.findByStatusAndWriterInfo(
                status = Status.SHARED,
                name = name,
                grade = grade,
                classNum = classNum,
                majorId = request.majorId
            )
        }

        return DocumentListResponse(
            documentList.map {
                DocumentResponse(it)
            }
        )
    }
}