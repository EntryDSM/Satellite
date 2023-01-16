package kr.hs.entrydsm.exit.domain.document.presentation.dto.response

import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.element.*
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.major.presentation.dto.response.MajorVO
import java.util.*

data class DocumentInfoResponse(

    val documentId: UUID,

    val writer: WriterInfoResponse,

    val status: Status,

    val introduce: IntroduceResponse,

    val skillSet: List<String>,

    val projectList: List<ProjectResponse>,

    val awardList: List<AwardResponse>,

    val certificateList: List<CertificateResponse>
) {
    constructor(document: Document) : this(
        documentId = document.id,
        writer = WriterInfoResponse(document.writer),
        status = document.status,
        introduce = IntroduceResponse(document.introduce),
        skillSet = document.skillSet,
        projectList = document.projectList.map { ProjectResponse(it) },
        awardList = document.awardList.map { AwardResponse(it) },
        certificateList = document.certificateList.map { CertificateResponse(it) }
    )

    data class WriterInfoResponse(
        val elementId: UUID,
        val studentId: UUID,
        val name: String,
        val profileImagePath: String,
        val studentNumber: String,
        val email: String,
        val major: MajorVO
    ) {
        constructor(element: WriterInfoElement) : this(
            elementId = element.elementId,
            studentId = element.studentId,
            name = element.name,
            profileImagePath = element.profileImagePath,
            studentNumber = element.studentNumber,
            email = element.email,
            major = MajorVO(
                id = element.majorId,
                name = element.majorName
            )
        )
    }

    data class IntroduceResponse(
        val elementId: UUID,
        val heading: String,
        val introduce: String
    ) {
        constructor(element: IntroduceElement) : this(
            elementId = element.elementId,
            heading = element.heading,
            introduce = element.introduce
        )
    }

    data class ProjectResponse(
        val elementId: UUID,
        val name: String,
        val representImagePath: String,
        val startDate: Date,
        val endDate: Date,
        val skillSet: List<String>,
        val description: String,
        val url: String?
    ) {
        constructor(element: ProjectElement) : this(
            elementId = element.elementId,
            name = element.name,
            representImagePath = element.representImagePath,
            startDate = element.startDate,
            endDate = element.endDate,
            skillSet = element.skillSet,
            description = element.description,
            url = element.url
        )
    }

    data class AwardResponse(
        val elementId: UUID,
        val name: String,
        val awardingInstitution: String,
        val date: Date,
        val description: String?,
        val url: String?
    ) {
        constructor(element: AwardElement) : this(
            elementId = element.elementId,
            name = element.name,
            awardingInstitution = element.awardingInstitution,
            date = element.date,
            description = element.description,
            url = element.url
        )
    }

    data class CertificateResponse(
        val elementId: UUID,
        val name: String,
        val issuingInstitution: String,
        val date: Date
    ) {
        constructor(element: CertificateElement) : this(
            elementId = element.elementId,
            name = element.name,
            issuingInstitution = element.issuingInstitution,
            date = element.date
        )
    }
}