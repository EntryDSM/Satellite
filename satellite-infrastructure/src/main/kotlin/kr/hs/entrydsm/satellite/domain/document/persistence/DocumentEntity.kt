package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document("documents")
class DocumentEntity(
    id: UUID,
    year: Int,
    writer: WriterInfoElement,
    status: DocumentStatus,
    introduce: IntroduceElement,
    skillSet: MutableList<String>,
    projectList: MutableList<ProjectElement>,
    awardList: MutableList<AwardElement>,
    certificateList: MutableList<CertificateElement>,
    isDeleted: Boolean
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
) {
    companion object {
        fun of(document: Document) = document.run {
            DocumentEntity(
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
        }
    }
}