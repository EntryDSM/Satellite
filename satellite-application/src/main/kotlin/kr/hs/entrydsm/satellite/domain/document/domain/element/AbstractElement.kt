package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.UUID

abstract class AbstractElement(
    elementId: UUID? = UUID.randomUUID()
) {
    open val elementId: UUID = elementId ?: UUID.randomUUID()

    open val elementName: String
        get() = ""
}