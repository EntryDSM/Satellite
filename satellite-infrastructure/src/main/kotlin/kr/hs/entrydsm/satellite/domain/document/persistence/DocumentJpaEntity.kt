package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import org.hibernate.annotations.Where
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
@Where(clause = "is_deleted is false")
class DocumentJpaEntity(

    id: UUID,

    @Field("documentYear")
    override val year: Int,

    @Field("documentWriter")
    override val writer: WriterInfoElement,

    @Field("documentStatus")
    override val status: DocumentStatus,

    @Field("documentIntroduce")
    override val introduce: IntroduceElement,

    @Field("documentSkillSet")
    override val skillSet: MutableList<String>,

    @Field("documentProjectList")
    override val projectList: MutableList<ProjectElement>,

    @Field("documentAwardList")
    override val awardList: MutableList<AwardElement>,

    @Field("documentCertificateList")
    override val certificateList: MutableList<CertificateElement>,

    @Field("documentIsDeleted")
    override val isDeleted: Boolean

) : Document(
    id = id,
    year = year,
    writer = writer,
    status = status,
    introduce = introduce,
    skillSet = skillSet,
    projectList = projectList,
    awardList = awardList,
    certificateList = certificateList,
    isDeleted = isDeleted
)