package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort

@ReadOnlyUseCase
class QueryMyDocumentInfoUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort
) {
    fun execute(): DocumentInfoResponse {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackList = feedbackPort.queryByDocumentId(document.id)
        val feedbackMap = feedbackList.associate { it.elementId to it.comment }

        return DocumentInfoResponse(document, feedbackMap)
    }
}