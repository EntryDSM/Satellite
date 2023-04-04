package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.Id

@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
@Where(clause = "is_deleted is false")
class DocumentJpaEntity(

    @Id
    override val id: UUID,

    override val year: Int,

    override val writer: WriterInfoElement,

    override val status: DocumentStatus,

    override val introduce: IntroduceElement,

    override val skillSet: MutableList<String>,

    override val projectList: MutableList<ProjectElement>,

    override val awardList: MutableList<AwardElement>,

    override val certificateList: MutableList<CertificateElement>,

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