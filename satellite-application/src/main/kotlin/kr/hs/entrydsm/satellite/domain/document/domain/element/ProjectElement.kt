package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class ProjectElement(
    elementId: UUID?,
    val name: String,
    val representImagePath: String,
    val startDate: Date,
    val endDate: Date?,
    val isPeriod: Boolean?,
    val skillSet: List<String>,
    val description: String,
    val url: String?
) : AbstractElement(elementId) {

    override val elementName: String
        get() = "프로젝트 $name"
}