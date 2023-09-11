package kr.hs.entrydsm.satellite.domain.document.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.ActivityElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document("documents")
data class DocumentEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override val year: Int,
    override var isDeleted: Boolean,
    override var status: DocumentStatus,
    override var writer: WriterInfoElement,
    override var introduce: IntroduceElement,
    override var skillSet: List<String>,
    override var projectList: List<ProjectElement>,
    override var awardList: List<AwardElement>,
    override var certificateList: List<CertificateElement>,
    override var activityList: List<ActivityElement>?
) : Document, BaseUUIDEntity(id)