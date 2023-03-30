package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class AwardElement(

    elementId: UUID? = null,
    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?,
    val url: String?

) : AbstractElement(elementId) {

    override val elementName: String
        get() = "수상경력 $name"
}