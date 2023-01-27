package kr.hs.entrydsm.exit.domain.student.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.persistence.repository.findByWriterInfo
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.QueryDocumentRequest
import kr.hs.entrydsm.exit.domain.student.presentation.dto.response.StudentDocumentListResponse
import kr.hs.entrydsm.exit.domain.student.presentation.dto.response.StudentDocumentListResponse.StudentDocumentResponse

@ReadOnlyUseCase
class QueryStudentDocumentListUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: QueryDocumentRequest): StudentDocumentListResponse {

        val documentList = request.run {
            documentRepository.findByWriterInfo(
                name = name!!,
                grade = grade,
                classNum = classNum,
                majorId = request.majorId
            )
        }

        return StudentDocumentListResponse(
            documentList.map { StudentDocumentResponse(it) }
        )
    }
}