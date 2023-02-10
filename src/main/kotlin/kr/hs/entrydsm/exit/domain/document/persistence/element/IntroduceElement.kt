package kr.hs.entrydsm.exit.domain.document.persistence.element

import javax.persistence.Transient

class IntroduceElement (

    val heading: String = "",
    val introduce: String = ""

) : AbstractElement() {
    @get:Transient
    override val elementName: String
        get() = "자기소개"
}