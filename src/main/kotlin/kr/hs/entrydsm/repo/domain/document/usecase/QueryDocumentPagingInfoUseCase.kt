package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.QueryDocumentPagingInfoResponse
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

@ReadOnlyUseCase
class QueryDocumentPagingInfoUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(
        documentId: UUID,
        status: Status
    ): QueryDocumentPagingInfoResponse {

        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        val previousDocument = documentRepository
            .findTopByStatusAndWriterStudentNumberIsLessThanOrderByWriterStudentNumberDesc(
                studentNumber = document.writer.studentNumber,
                status = status
            )

        val nextDocument = documentRepository
            .findTopByStatusAndWriterStudentNumberIsGreaterThanOrderByWriterStudentNumber(
                studentNumber = document.writer.studentNumber,
                status = status
            )

        return QueryDocumentPagingInfoResponse(
            hasPrevious = previousDocument != null,
            previousDocumentId = previousDocument?.id,
            hasNext = nextDocument != null,
            nextDocumentId = nextDocument?.id
        )
    }
}