package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import java.util.*

open class AwardRequest(
    open val elementId: UUID?,
    open val name: String,
    open val awardingInstitution: String,
    open val date: Date,
    open val description: String?
) {
    fun toAwardElement() = AwardElement(
        elementId = elementId,
        name = name,
        awardingInstitution = awardingInstitution,
        date = date,
        description = description
    )
}