package kr.hs.entrydsm.satellite.domain.document.domain.element

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

abstract class AbstractElement : Domain {
    abstract val elementId: UUID
    abstract val elementName: String
}
