package kr.hs.entrydsm.satellite.domain.student.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.dto.StudentDocumentListResponse
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentListUseCase(
    private val documentPort: DocumentPort
) {
    fun execute(
        name: String?,
        grade: Int?,
        classNum: Int?,
        majorId: UUID?
    ): StudentDocumentListResponse {

        val documentList = documentPort.queryByWriterInfo(
            name = name,
            grade = grade,
            classNum = classNum,
            majorId = majorId
        )

        return StudentDocumentListResponse(
            documentList.map { StudentDocumentListResponse.StudentDocumentResponse(it) }
        )
    }
}