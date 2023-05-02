package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort

@ReadOnlyUseCase
class QueryMyDocumentInfoUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort,
    private val filePort: FilePort
) {
    suspend fun execute(): DocumentInfoResponse {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackMap = feedbackPort.queryByDocumentId(document.id)
            .associate { it.elementId to it.comment }

        val fileBaseUrl = filePort.getFileBaseUrl()
        return DocumentInfoResponse(fileBaseUrl, document, feedbackMap)
    }
}