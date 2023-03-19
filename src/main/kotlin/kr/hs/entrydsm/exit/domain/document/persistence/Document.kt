package kr.hs.entrydsm.exit.domain.document.persistence

import kr.hs.entrydsm.exit.domain.document.persistence.element.AwardElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.CertificateElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.IntroduceElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.ProjectElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.entity.BaseMongoUUIDEntity
import java.util.UUID

@org.springframework.data.mongodb.core.mapping.Document(collection="documents")
class Document(

    id: UUID? = null,

    val year: Int,

    val writer: WriterInfoElement,

    val status: Status,

    val introduce: IntroduceElement = IntroduceElement(),

    val skillSet: MutableList<String> = mutableListOf(),

    val projectList: MutableList<ProjectElement> = mutableListOf(),

    val awardList: MutableList<AwardElement> = mutableListOf(),

    val certificateList: MutableList<CertificateElement> = mutableListOf()

): BaseMongoUUIDEntity(id) {

    fun isWriter(student: Student?) = writer.studentId == student?.id

    fun getElementNameMap(): Map<UUID, String> {
        val elementList = listOf(
            writer,
            introduce,
            *projectList.toTypedArray(),
            *awardList.toTypedArray(),
            *certificateList.toTypedArray()
        )
        return elementList.associate { it.elementId to it.elementName}
    }

    fun updateWriterInfo(writer: WriterInfoElement) =
        copy(writer = writer)

    fun updateStatus(status: Status) =
        copy(status = status)

    fun updateIntroduce(introduce: IntroduceElement) =
        copy(introduce = introduce)

    fun updateSkillSet(skillSet: MutableList<String>) =
        copy(skillSet = skillSet)

    fun updateProject(projectList: MutableList<ProjectElement>) =
        copy(projectList = projectList)

    fun updateAwardList(awardList: MutableList<AwardElement>) =
        copy(awardList = awardList)

    fun updateCertificateList(certificateList: MutableList<CertificateElement>) =
        copy(certificateList = certificateList)

    private fun copy(
        writer: WriterInfoElement = this.writer,
        year: Int = this.year,
        status: Status = this.status,
        introduce: IntroduceElement = this.introduce,
        skillSet: MutableList<String> = this.skillSet,
        projectList: MutableList<ProjectElement> = this.projectList,
        awardList: MutableList<AwardElement> = this.awardList,
        certificateList: MutableList<CertificateElement> = this.certificateList
    ): Document {
        return Document(
            id = this.id,
            writer = writer,
            year = year,
            status = status,
            introduce = introduce,
            skillSet = skillSet,
            projectList = projectList,
            awardList = awardList,
            certificateList = certificateList
        )
    }
}