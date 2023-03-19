package kr.hs.entrydsm.repo.domain.document.persistence.element

import java.util.Date
import java.util.UUID
import javax.persistence.Transient

class AwardElement(

    elementId: UUID? = null,
    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?,
    val url: String?

) : AbstractElement(elementId) {
    @get:Transient
    override val elementName: String
        get() = "수상경력 $name"
}