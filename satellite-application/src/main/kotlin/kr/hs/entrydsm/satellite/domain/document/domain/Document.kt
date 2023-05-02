package kr.hs.entrydsm.satellite.domain.document.domain

import kr.hs.entrydsm.satellite.domain.document.domain.element.AbstractElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface Document {
    val id: UUID
    val year: Int
    var isDeleted: Boolean
    var status: DocumentStatus
    var writer: WriterInfoElement
    var introduce: IntroduceElement
    var skillSet: List<String>
    var projectList: List<ProjectElement>
    var awardList: List<AwardElement>
    var certificateList: List<CertificateElement>

    fun isWriter(studentId: UUID?) = writer.studentId == studentId

    private fun getElementList(): List<AbstractElement> =
        listOf(
            writer,
            introduce,
            *projectList.toTypedArray(),
            *awardList.toTypedArray(),
            *certificateList.toTypedArray()
        )

    fun getElementNameMap(): Map<UUID, String> {
        return getElementList().associate { it.elementId to it.elementName }
    }

    fun delete() { this.isDeleted = true }

    fun changeStatus(status: DocumentStatus) { this.status = status }

    fun updateElement(
        writer: WriterInfoElement = this.writer,
        introduce: IntroduceElement = this.introduce,
        skillSet: List<String> = this.skillSet,
        projectList: List<ProjectElement> = this.projectList,
        awardList: List<AwardElement> = this.awardList,
        certificateList: List<CertificateElement> = this.certificateList
    ): Document {
        this.writer = writer
        this.introduce = introduce
        this.skillSet = skillSet
        this.projectList = projectList
        this.awardList = awardList
        this.certificateList = certificateList
        return this
    }
}

data class DocumentDomain(
    override val id: UUID = UUID.randomUUID(),
    override val year: Int,
    override var isDeleted: Boolean = false,
    override var status: DocumentStatus = DocumentStatus.CREATED,
    override var writer: WriterInfoElement,
    override var introduce: IntroduceElement = IntroduceElement(),
    override var skillSet: List<String> = listOf(),
    override var projectList: List<ProjectElement> = listOf(),
    override var awardList: List<AwardElement> = listOf(),
    override var certificateList: List<CertificateElement> = listOf()
) : Document, Domain