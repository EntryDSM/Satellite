package kr.hs.entrydsm.satellite.domain.document.persistence.element

import java.util.UUID
import javax.persistence.Transient

abstract class AbstractElement(
    elementId: UUID? = UUID.randomUUID()
) {
    open val elementId: UUID = elementId ?: UUID.randomUUID()

    @get:Transient
    open val elementName: String
        get() = ""
}