package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class IntroduceElement(
    elementId: UUID? = null,
    val heading: String = "",
    val introduce: String = ""
) : AbstractElement(elementId) {

    override val elementName: String
        get() = "자기소개"
}