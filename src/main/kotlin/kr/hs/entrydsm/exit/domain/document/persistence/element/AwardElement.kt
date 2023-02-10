package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

class AwardElement(

    val name: String,
    val awardingInstitution: String,
    val date: Date,
    val description: String?,
    val url: String?

) : AbstractElement() {
    @get:Transient
    override val elementName: String
        get() = "수상경력 $name"
}