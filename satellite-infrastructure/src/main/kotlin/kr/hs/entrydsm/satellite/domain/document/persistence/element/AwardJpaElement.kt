package kr.hs.entrydsm.satellite.domain.document.persistence.element

import com.querydsl.core.annotations.QueryEmbeddable
import kr.hs.entrydsm.satellite.domain.document.domain.element.AwardElement
import java.util.*

@QueryEmbeddable
class AwardJpaElement(
    elementId: UUID,
    name: String,
    awardingInstitution: String,
    date: Date,
    description: String?,
    url: String?
) : AwardElement(
    elementId, name, awardingInstitution, date, description, url
) {
    companion object {
        fun of(awardElement: AwardElement) = awardElement.run {
            AwardJpaElement(
                elementId, name, awardingInstitution, date, description, url
            )
        }
    }
}