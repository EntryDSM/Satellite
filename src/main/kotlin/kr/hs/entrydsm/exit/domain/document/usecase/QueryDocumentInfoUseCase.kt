package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.domain.auth.Authority.TEACHER
import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import java.util.*

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

    private fun hasAccess(status: Status, authority: Authority): Boolean {
        return when(status) {
            Status.CREATED -> false
            Status.SUBMITTED -> authority == TEACHER
            Status.SHARED -> true
        }
    }

    private fun isWriter(document: Document) : Boolean {
        val student = studentRepository.findByIdOrNull(SecurityUtil.getCurrentUserId())
        return document.isWriter(student)
    }
}