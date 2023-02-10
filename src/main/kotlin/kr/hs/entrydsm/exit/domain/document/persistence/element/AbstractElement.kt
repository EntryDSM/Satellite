package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

abstract class AbstractElement(
    elementId: UUID? = UUID.randomUUID()
) {
    open val elementId: UUID = elementId ?: UUID.randomUUID()

    @get:Transient
    open val elementName: String
        get() = ""
}