package kr.hs.entrydsm.satellite.domain.student.presentation.dto.response

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.response.MajorVO

data class StudentDocumentListResponse(

    val studentList: List<StudentDocumentResponse>
) {
    data class StudentDocumentResponse(
        val studentId: UUID,
        val name: String,
        val documentStatus: Status,
        val documentId: UUID,
        val profileImagePath: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorVO
    ) {
        constructor(document: Document) : this(
            studentId = document.writer.studentId,
            name = document.writer.name,
            documentStatus = document.status,
            documentId = document.id,
            profileImagePath = document.writer.profileImagePath,
            studentNumber = document.writer.studentNumber,
            email = document.writer.email,
            major = MajorVO(
                id = document.writer.majorId,
                name = document.writer.majorName
            )
        )
    }
}