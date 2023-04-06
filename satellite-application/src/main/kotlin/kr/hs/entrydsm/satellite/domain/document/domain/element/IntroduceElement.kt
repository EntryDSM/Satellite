package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

data class IntroduceElement(
    override val elementId: UUID = UUID.randomUUID(),
    val heading: String = "",
    val introduce: String = ""
) : AbstractElement() {

    override val elementName: String
        get() = "자기소개"
}