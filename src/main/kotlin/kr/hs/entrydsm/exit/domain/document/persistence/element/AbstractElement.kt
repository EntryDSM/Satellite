package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

abstract class AbstractElement(
    val elementId: UUID = UUID.randomUUID()
) {
    @get:Transient
    open val elementName: String
        get() = ""
}