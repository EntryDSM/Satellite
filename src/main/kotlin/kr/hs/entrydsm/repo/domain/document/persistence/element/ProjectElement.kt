package kr.hs.entrydsm.repo.domain.document.persistence.element

import java.util.Date
import java.util.UUID
import javax.persistence.Transient

class ProjectElement(

    elementId: UUID? = null,
    val name: String,
    val representImagePath: String,
    val startDate: Date,
    val endDate: Date,
    val skillSet: List<String>,
    val description: String,
    val url: String?

) : AbstractElement(elementId) {
    @get:Transient
    override val elementName: String
        get() = "프로젝트 $name"
}