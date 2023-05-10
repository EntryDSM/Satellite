package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import java.util.*

open class IntroduceRequest(
    open val elementId: UUID?,
    open val heading: String,
    open val introduce: String
) {
    fun toIntroduceElement(elementId: UUID?) =
        IntroduceElement(
            elementId = elementId,
            heading = heading,
            introduce = this.introduce
        )
}