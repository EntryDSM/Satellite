package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

data class ProjectElement(
    override val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val representImagePath: String,
    val startDate: Date,
    val endDate: Date,
    val skillSet: List<String>,
    val description: String,
    val url: String?
) : AbstractElement() {

    override val elementName: String
        get() = "프로젝트 $name"
}