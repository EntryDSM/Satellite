package kr.hs.entrydsm.exit.domain.document.persistence

import kr.hs.entrydsm.exit.domain.document.persistence.element.*
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.entity.BaseMongoUUIDEntity
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document(collection="documents")
class Document(

    id: UUID? = null,

    val writer: WriterInfoElement,

    val status: Status,

    val introduce: IntroduceElement = IntroduceElement(),

    val skillSet: MutableList<String> = mutableListOf(),

    val projectList: MutableList<ProjectElement> = mutableListOf(),

    val awardList: MutableList<AwardElement> = mutableListOf(),

    val certificateList: MutableList<CertificateElement> = mutableListOf()

): BaseMongoUUIDEntity(id) {

    fun isWriter(student: Student?) = writer.studentId == student?.id

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
            status = status,
            introduce = introduce,
            skillSet = skillSet,
            projectList = projectList,
            awardList = awardList,
            certificateList = certificateList
        )
    }
}