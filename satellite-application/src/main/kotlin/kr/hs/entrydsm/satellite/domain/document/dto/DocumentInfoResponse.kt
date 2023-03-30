package kr.hs.entrydsm.satellite.domain.document.dto

import java.util.Date
import java.util.UUID
import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.response.MajorVO

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
    constructor(documentJpaEntity: DocumentJpaEntity) : this(
        documentId = documentJpaEntity.id,
        writer = WriterInfoResponse(documentJpaEntity.writer, null),
        documentStatus = documentJpaEntity.status,
        introduce = IntroduceResponse(documentJpaEntity.introduce, null),
        skillSet = documentJpaEntity.skillSet,
        projectList = documentJpaEntity.projectList.map { ProjectResponse(it, null) },
        awardList = documentJpaEntity.awardList.map { AwardResponse(it, null) },
        certificateList = documentJpaEntity.certificateList.map { CertificateResponse(it, null) }
    )

    constructor(documentJpaEntity: DocumentJpaEntity, feedbackMap: Map<UUID, String>) : this(
        documentId = documentJpaEntity.id,
        writer = WriterInfoResponse(documentJpaEntity.writer, feedbackMap[documentJpaEntity.writer.elementId]),
        documentStatus = documentJpaEntity.status,
        introduce = IntroduceResponse(documentJpaEntity.introduce, feedbackMap[documentJpaEntity.introduce.elementId]),
        skillSet = documentJpaEntity.skillSet,
        projectList = documentJpaEntity.projectList.map { ProjectResponse(it, feedbackMap[it.elementId]) },
        awardList = documentJpaEntity.awardList.map { AwardResponse(it, feedbackMap[it.elementId]) },
        certificateList = documentJpaEntity.certificateList.map { CertificateResponse(it, feedbackMap[it.elementId]) }
    )

    data class WriterInfoResponse(
        val elementId: UUID,
        val studentId: UUID,
        val name: String,
        val profileImagePath: String,
        val studentNumber: Int,
        val email: String,
        val major: MajorVO,
        val feedback: String?
    ) {
        constructor(element: WriterInfoElement, feedback: String?) : this(
            elementId = element.elementId,
            studentId = element.studentId,
            name = element.name,
            profileImagePath = element.profileImagePath,
            studentNumber = element.studentNumber,
            email = element.email,
            major = MajorVO(
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