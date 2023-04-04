package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.UUID
import java.util.Date

data class AwardElement(

    override val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?,
    val url: String?

) : AbstractElement(elementId) {

    override val elementName: String
        get() = "수상경력 $name"
}