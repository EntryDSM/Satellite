package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.ActivityElement
import kr.hs.entrydsm.satellite.domain.document.exception.InvalidPeriodException
import java.util.*

open class ActivityRequest(
    open val elementId: UUID?,
    open val name: String,
    open val date: Date,
    open val endDate: Date?,
    open val isPeriod: Boolean,
    open val description: String?
) {
    fun toActivityElement() = let {
        if ((!isPeriod && endDate != null) || (isPeriod && endDate == null)) {
            throw InvalidPeriodException
        }
        ActivityElement(
            elementId = elementId,
            name = name,
            date = date,
            endDate = endDate,
            isPeriod = isPeriod,
            description = description
        )
    }
}