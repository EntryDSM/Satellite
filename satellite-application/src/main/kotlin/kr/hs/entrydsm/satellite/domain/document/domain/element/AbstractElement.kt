package kr.hs.entrydsm.satellite.domain.document.domain.element

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

abstract class AbstractElement(
    elementId: UUID? = UUID.randomUUID()
) : Domain {

    val elementId: UUID = elementId ?: UUID.randomUUID()

    abstract val elementName: String
}