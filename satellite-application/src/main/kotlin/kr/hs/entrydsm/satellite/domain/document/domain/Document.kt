package kr.hs.entrydsm.satellite.domain.document.domain

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

    fun getElementNameMap(): Map<UUID, String> {
        val elementList = listOf(
            writer,
            introduce,
            *projectList.toTypedArray(),
            *awardList.toTypedArray(),
            *certificateList.toTypedArray()
        )
        return elementList.associate { it.elementId to it.elementName }
    }

    fun delete() { this.isDeleted = true }

    fun changeStatus(status: DocumentStatus) { this.status = status }

    fun updateElement(
        writer: WriterInfoElement = this.writer,
        status: DocumentStatus = this.status,
        introduce: IntroduceElement = this.introduce
    ) {
        this.writer = writer
        this.status = status
        this.introduce = introduce
    }
}

data class DocumentDomain(

    override val id: UUID = UUID.randomUUID(),

    override val year: Int,

    override var isDeleted: Boolean = false,

    override var status: DocumentStatus = DocumentStatus.CREATED,

    override var writer: WriterInfoElement,

    override var introduce: IntroduceElement = IntroduceElement(),

    override var skillSet: List<String> = mutableListOf(),

    override var projectList: List<ProjectElement> = mutableListOf(),

    override var awardList: List<AwardElement> = mutableListOf(),

    override var certificateList: List<CertificateElement> = mutableListOf()

) : Document, Domain