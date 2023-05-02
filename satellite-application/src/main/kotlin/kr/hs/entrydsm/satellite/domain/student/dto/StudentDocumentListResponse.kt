package kr.hs.entrydsm.satellite.domain.student.dto

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement
import java.util.*


data class StudentDocumentListResponse(
    val studentList: List<StudentDocumentResponse>
) {
    data class StudentDocumentResponse(
        val studentId: UUID,
        val name: String,
        val documentStatus: DocumentStatus,
        val documentId: UUID,
        val profileImageUrl: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorElement,
        val feedbackCount: Int
    ) {
        constructor(fileBaseUrl: String, document: Document, feedbackCount: Int) : this(
            studentId = document.writer.studentId,
            name = document.writer.name,
            documentStatus = document.status,
            documentId = document.id,
            profileImageUrl = fileBaseUrl + document.writer.profileImagePath,
            studentNumber = document.writer.studentNumber,
            email = document.writer.email,
            major = MajorElement(
                id = document.writer.majorId,
                name = document.writer.majorName
            ),
            feedbackCount = feedbackCount
        )
    }
}