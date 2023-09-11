package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.ActivityElement
import java.util.*

open class ActivityRequest(
    open val elementId: UUID?,
    open val name: String,
    open val date: Date,
    open val description: String?
) {
    fun toActivityElement() = ActivityElement(
        elementId = elementId,
        name = name,
        date = date,
        description = description
    )
}