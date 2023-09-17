package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class ActivityElement(
    elementId: UUID? = null,
    val name: String,
    val date: Date,
    val endDate: Date?,
    val isPeriod: Boolean?,
    val description: String?
) : AbstractElement(elementId) {

    override val elementName: String
        get() = "활동 $name"
}