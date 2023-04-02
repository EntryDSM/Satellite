package kr.hs.entrydsm.satellite.domain.document.domain

import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

open class Document(

    open val id: UUID = UUID.randomUUID(),

    open val year: Int,

    open val writer: WriterInfoElement,

    open val status: DocumentStatus,

    open val introduce: IntroduceElement = IntroduceElement(),

    open val skillSet: MutableList<String> = mutableListOf(),

    open val projectList: MutableList<ProjectElement> = mutableListOf(),

    open val awardList: MutableList<AwardElement> = mutableListOf(),

    open val certificateList: MutableList<CertificateElement> = mutableListOf(),

    open val isDeleted: Boolean = false

) : Domain {

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

    fun delete() =
        copy(isDeleted = true)

    fun copy(
        id: UUID = this.id,
        year: Int = this.year,
        writer: WriterInfoElement = this.writer,
        status: DocumentStatus = this.status,
        introduce: IntroduceElement = this.introduce,
        skillSet: MutableList<String> = this.skillSet,
        projectList: MutableList<ProjectElement> = this.projectList,
        awardList: MutableList<AwardElement> = this.awardList,
        certificateList: MutableList<CertificateElement> = this.certificateList,
        isDeleted: Boolean = this.isDeleted
    ) = Document(
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