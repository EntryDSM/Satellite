package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*

class IntroduceElement (

    val elementId: UUID = UUID.randomUUID(),
    val heading: String = "",
    val introduce: String = ""

)