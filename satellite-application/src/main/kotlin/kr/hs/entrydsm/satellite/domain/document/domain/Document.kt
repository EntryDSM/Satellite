package kr.hs.entrydsm.satellite.domain.document.domain

import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import java.util.*

data class Document(

    val id: UUID = UUID(0,0),

    val year: Int,

    val writer: WriterInfoElement,

    val status: DocumentStatus,

    val introduce: IntroduceElement = IntroduceElement(),

    val skillSet: MutableList<String> = mutableListOf(),

    val projectList: MutableList<ProjectElement> = mutableListOf(),

    val awardList: MutableList<AwardElement> = mutableListOf(),

    val certificateList: MutableList<CertificateElement> = mutableListOf(),

    val isDeleted: Boolean = false

) {

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
}