package kr.hs.entrydsm.satellite.domain.student.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.student.dto.StudentDocumentListResponse
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentListUseCase(
    private val documentPort: DocumentPort,
    private val feedbackPort: FeedbackPort,
    private val filePort: FilePort
) {
    suspend fun execute(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?
    ): StudentDocumentListResponse {

        val documentList = documentPort.queryByWriterInfoAndStatus(
            name = name,
            grade = grade,
            classNum = classNum,
            majorId = majorId
        )

        val feedbacks = feedbackPort.queryByDocumentIdIn(documentList.map { it.id })

        val fileBaseUrl = filePort.getFileBaseUrl()
        return StudentDocumentListResponse(
            documentList.map {
                StudentDocumentListResponse.StudentDocumentResponse(
                    fileBaseUrl = fileBaseUrl,
                    document = it,
                    feedbackCount = feedbacks.count { f -> f.documentId == it.id }
                )
            }
        )
    }
}