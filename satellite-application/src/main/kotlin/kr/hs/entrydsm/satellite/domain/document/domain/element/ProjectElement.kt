package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.Date

data class ProjectElement(
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