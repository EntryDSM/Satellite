package kr.hs.entrydsm.repo.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

class IntroduceElement (

    elementId: UUID? = null,
    val heading: String = "",
    val introduce: String = ""

) : AbstractElement(elementId) {
    @get:Transient
    override val elementName: String
        get() = "자기소개"
}