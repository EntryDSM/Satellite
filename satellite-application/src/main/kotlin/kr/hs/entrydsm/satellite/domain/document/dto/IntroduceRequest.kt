package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement

open class IntroduceRequest(
    open val heading: String,
    open val introduce: String
) {
    fun toIntroduceElement() = IntroduceElement(
        heading = heading,
        introduce = introduce
    )
}