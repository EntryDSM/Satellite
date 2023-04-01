package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement
import java.util.*

data class DocumentInfoResponse(

    val documentId: UUID,

    val writer: WriterInfoResponse,

    val documentStatus: DocumentStatus,

    val introduce: IntroduceResponse,

    val skillSet: List<String>,

    val projectList: List<ProjectResponse>,

    val awardList: List<AwardResponse>,

    val certificateList: List<CertificateResponse>
) {
    constructor(document: Document) : this(
        documentId = document.id,
        writer = WriterInfoResponse(document.writer, null),
        documentStatus = document.status,
        introduce = IntroduceResponse(document.introduce, null),
        skillSet = document.skillSet,
        projectList = document.projectList.map { ProjectResponse(it, null) },
        awardList = document.awardList.map { AwardResponse(it, null) },
        certificateList = document.certificateList.map { CertificateResponse(it, null) }
    )

    constructor(document: Document, feedbackMap: Map<UUID, String>) : this(
        documentId = document.id,
        writer = WriterInfoResponse(document.writer, feedbackMap[document.writer.elementId]),
        documentStatus = document.status,
        introduce = IntroduceResponse(document.introduce, feedbackMap[document.introduce.elementId]),
        skillSet = document.skillSet,
        projectList = document.projectList.map { ProjectResponse(it, feedbackMap[it.elementId]) },
        awardList = document.awardList.map { AwardResponse(it, feedbackMap[it.elementId]) },
        certificateList = document.certificateList.map { CertificateResponse(it, feedbackMap[it.elementId]) }
    )

    data class WriterInfoResponse(
        val elementId: UUID,
        val studentId: UUID,
        val name: String,
        val profileImagePath: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorElement,
        val feedback: String?
    ) {
        constructor(element: WriterInfoElement, feedback: String?) : this(
            elementId = element.elementId,
            studentId = element.studentId,
            name = element.name,
            profileImagePath = element.profileImagePath,
            studentNumber = element.studentNumber,
            email = element.email,
            major = MajorElement(
                id = element.majorId,
                name = element.majorName
            ),
            feedback = feedback
        )
    }

    data class IntroduceResponse(
        val elementId: UUID,
        val heading: String,
        val introduce: String,
        val feedback: String?
    ) {
        constructor(element: IntroduceElement, feedback: String?) : this(
            elementId = element.elementId,
            heading = element.heading,
            introduce = element.introduce,
            feedback = feedback
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
        val url: String?,
        val feedback: String?
    ) {
        constructor(element: ProjectElement, feedback: String?) : this(
            elementId = element.elementId,
            name = element.name,
            representImagePath = element.representImagePath,
            startDate = element.startDate,
            endDate = element.endDate,
            skillSet = element.skillSet,
            description = element.description,
            url = element.url,
            feedback = feedback
        )
    }

    data class AwardResponse(
        val elementId: UUID,
        val name: String,
        val awardingInstitution: String,
        val date: Date,
        val description: String?,
        val url: String?,
        val feedback: String?
    ) {
        constructor(element: AwardElement, feedback: String?) : this(
            elementId = element.elementId,
            name = element.name,
            awardingInstitution = element.awardingInstitution,
            date = element.date,
            description = element.description,
            url = element.url,
            feedback = feedback
        )
    }

    data class CertificateResponse(
        val elementId: UUID,
        val name: String,
        val issuingInstitution: String,
        val date: Date,
        val feedback: String?
    ) {
        constructor(element: CertificateElement, feedback: String?) : this(
            elementId = element.elementId,
            name = element.name,
            issuingInstitution = element.issuingInstitution,
            date = element.date,
            feedback = feedback
        )
    }
}