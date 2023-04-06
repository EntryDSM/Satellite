package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class AwardElement(
    override val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?
) : AbstractElement() {

    override val elementName: String
        get() = "수상경력 $name"
}