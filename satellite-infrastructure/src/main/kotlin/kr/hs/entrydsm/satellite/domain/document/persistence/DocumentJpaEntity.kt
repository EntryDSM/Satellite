package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.persistence.element.AwardJpaElement
import kr.hs.entrydsm.satellite.domain.document.persistence.element.CertificateJpaElement
import kr.hs.entrydsm.satellite.domain.document.persistence.element.IntroduceJpaElement
import kr.hs.entrydsm.satellite.domain.document.persistence.element.ProjectJpaElement
import kr.hs.entrydsm.satellite.domain.document.persistence.element.WriterInfoJpaElement
import org.hibernate.annotations.Where
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
@Where(clause = "is_deleted is false")
class DocumentJpaEntity(
    id: UUID,
    year: Int,
    writer: WriterInfoJpaElement,
    status: DocumentStatus,
    introduce: IntroduceJpaElement,
    skillSet: MutableList<String>,
    projectList: MutableList<ProjectJpaElement>,
    awardList: MutableList<AwardJpaElement>,
    certificateList: MutableList<CertificateJpaElement>,
    isDeleted: Boolean
) : Document(
    id = id,
    year = year,
    writer = writer as WriterInfoElement,
    status = status,
    introduce = introduce as IntroduceElement,
    skillSet = skillSet,
    projectList = projectList as MutableList<ProjectElement>,
    awardList = awardList as MutableList<AwardElement>,
    certificateList = certificateList as MutableList<CertificateElement>,
    isDeleted = isDeleted
) {
    companion object {
        fun of(document: Document) = document.run {
            DocumentJpaEntity(
                id = id,
                year = year,
                writer = WriterInfoJpaElement.of(writer),
                status = status,
                introduce = IntroduceJpaElement.of(introduce),
                skillSet = skillSet,
                projectList = projectList.map { ProjectJpaElement.of(it) }.toMutableList(),
                awardList = awardList.map { AwardJpaElement.of(it) }.toMutableList(),
                certificateList = certificateList.map { CertificateJpaElement.of(it) }.toMutableList(),
                isDeleted = isDeleted
            )
        }
    }
}