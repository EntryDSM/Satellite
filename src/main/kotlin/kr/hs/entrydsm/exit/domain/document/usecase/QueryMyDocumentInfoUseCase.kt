package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.exit.global.security.SecurityUtil

@ReadOnlyUseCase
class QueryMyDocumentInfoUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(): DocumentInfoResponse {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        return DocumentInfoResponse(document)
    }
}