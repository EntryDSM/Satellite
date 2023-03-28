package kr.hs.entrydsm.satellite.domain.document.usecase

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.common.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

@ReadOnlyUseCase
class QueryDocumentInfoUseCase(
    private val documentRepository: DocumentRepository,
    private val studentRepository: StudentRepository
) {
    fun execute(documentId: UUID): DocumentInfoResponse {

        val authority = SecurityUtil.getCurrentUserAuthority()
        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if (!isWriter(document) && !hasAccess(document.status, authority)) {
            throw DocumentAccessRightException
        }

        return DocumentInfoResponse(document)
    }

    private fun hasAccess(status: Status, authority: Authority): Boolean {
        return when (status) {
            Status.CREATED -> false
            Status.SUBMITTED -> authority == Authority.TEACHER
            Status.SHARED -> true
        }
    }

    private fun isWriter(document: Document): Boolean {
        val student = studentRepository.findByIdOrNull(SecurityUtil.getCurrentUserId())
        return document.isWriter(student)
    }
}