package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import kr.hs.entrydsm.satellite.domain.file.domain.DefaultImages
import java.util.*

open class ProjectRequest(
    open val elementId: UUID?,
    open val name: String,
    open val representImagePath: String?,
    open val startDate: Date,
    open val endDate: Date,
    open val skillList: List<String>,
    open val description: String,
    open val url: String?
) {
    fun toProjectElement() = ProjectElement(
        elementId = elementId,
        name = name,
        representImagePath = representImagePath ?: DefaultImages.PROJECT,
        startDate = startDate,
        endDate = endDate,
        skillSet = skillList,
        description = description,
        url = url
    )
}