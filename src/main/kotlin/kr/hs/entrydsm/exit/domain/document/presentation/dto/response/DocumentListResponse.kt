package kr.hs.entrydsm.exit.domain.document.presentation.dto.response

import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.major.presentation.dto.response.MajorVO

data class DocumentListResponse(
    val documentList: List<DocumentResponse>
) {
    data class DocumentResponse(
        val name: String,
        val profileImagePath: String,
        val studentNumber: String,
        val email: String,
        val major: MajorVO
    ) {
        constructor(document: Document): this(
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

