package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement
import java.util.*

data class DocumentListResponse(
    val documentList: List<DocumentResponse>
) {
    data class DocumentResponse(
        val documentId: UUID,
        val name: String,
        val profileImagePath: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorElement
    ) {
        constructor(document: Document) : this(
            documentId = document.id,
            name = document.writer.name,
            profileImagePath = document.writer.profileImagePath,
            studentNumber = document.writer.studentNumber,
            email = document.writer.email,
            major = MajorElement(
                id = document.writer.majorId,
                name = document.writer.majorName
            )
        )
    }
}