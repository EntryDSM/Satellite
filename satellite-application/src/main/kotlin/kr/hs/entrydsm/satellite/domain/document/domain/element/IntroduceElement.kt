package kr.hs.entrydsm.satellite.domain.document.domain.element

data class IntroduceElement(
    val heading: String = "",
    val introduce: String = ""
) : AbstractElement() {

    override val elementName: String
        get() = "자기소개"
}