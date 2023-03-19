package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

@ReadOnlyUseCase
class QueryDocumentInfoUseCase(
    private val documentRepository: DocumentRepository,
    private val studentRepository: StudentRepository
) {

    fun execute(documentId: UUID): DocumentInfoResponse {

        val authority = SecurityUtil.getCurrentUserAuthority()
        val document = documentRepository.findByIdOrNull(documentId) ?: throw DocumentNotFoundException

        if(!isWriter(document) && !hasAccess(document.status, authority)) {
            throw DocumentAccessRightException
        }

        return DocumentInfoResponse(document)
    }

    private fun hasAccess(status: Status, authority: kr.hs.entrydsm.repo.domain.auth.constant.Authority): Boolean {
        return when(status) {
            Status.CREATED -> false
            Status.SUBMITTED -> authority == kr.hs.entrydsm.repo.domain.auth.constant.Authority.TEACHER
            Status.SHARED -> true
        }
    }

    private fun isWriter(document: Document) : Boolean {
        val student = studentRepository.findByIdOrNull(SecurityUtil.getCurrentUserId())
        return document.isWriter(student)
    }
}