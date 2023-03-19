package kr.hs.entrydsm.repo.domain.student.presentation.dto.response

import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.major.presentation.dto.response.MajorVO
import java.util.*

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
        constructor(document: Document): this(
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
