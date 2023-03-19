package kr.hs.entrydsm.repo.domain.document.presentation.dto.response

import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.major.presentation.dto.response.MajorVO
import java.util.UUID

data class DocumentListResponse(
    val documentList: List<DocumentResponse>
) {
    data class DocumentResponse(
        val documentId: UUID,
        val name: String,
        val profileImagePath: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorVO
    ) {
        constructor(document: Document): this(
            documentId = document.id,
            name = document.writer.name,
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

