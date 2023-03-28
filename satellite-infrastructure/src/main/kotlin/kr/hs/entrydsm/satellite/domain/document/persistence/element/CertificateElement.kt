package kr.hs.entrydsm.satellite.domain.document.persistence.element

import java.util.Date
import java.util.UUID
import javax.persistence.Transient

class CertificateElement(

    elementId: UUID? = null,
    val name: String,
    val issuingInstitution: String,
    val date: Date

) : AbstractElement(elementId) {
    @get:Transient
    override val elementName: String
        get() = "자격증 $name"
}