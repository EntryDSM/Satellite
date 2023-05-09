package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import java.util.*

@ReadOnlyUseCase
class QueryDocumentInfoUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val filePort: FilePort
) {
    suspend fun execute(documentId: UUID): DocumentInfoResponse {

        val authority = securityPort.getCurrentUserAuthority()
        val document = documentPort.queryById(documentId) ?: throw DocumentNotFoundException

        if (!isWriter(document, authority) && !hasAccess(document.status, authority)) {
            throw DocumentAccessRightException
        }

        return DocumentInfoResponse(document)
    }

    private fun hasAccess(documentStatus: DocumentStatus, authority: Authority): Boolean {
        return when (documentStatus) {
            DocumentStatus.CREATED -> false
            DocumentStatus.SUBMITTED -> authority == Authority.TEACHER
            DocumentStatus.SHARED -> true
        }
    }

    private suspend fun isWriter(document: Document,authority: Authority): Boolean {
        return if (authority == Authority.STUDENT) {
            val student = securityPort.getCurrentStudent()
            document.isWriter(student.id)
        } else false
    }
}