package kr.hs.entrydsm.satellite.domain.document.persistence.element

import com.querydsl.core.annotations.QueryEmbeddable
import kr.hs.entrydsm.satellite.domain.document.domain.element.IntroduceElement
import java.util.*

@QueryEmbeddable
class IntroduceJpaElement(
    elementId: UUID,
    heading: String,
    introduce: String
) : IntroduceElement(elementId, heading, introduce) {
    companion object {
        fun of(introduceElement: IntroduceElement) = introduceElement.run {
            IntroduceJpaElement(elementId, heading, introduce)
        }
    }
}