package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*
import javax.persistence.Transient

class CertificateElement(

    val name: String,
    val issuingInstitution: String,
    val date: Date

) : AbstractElement() {
    @get:Transient
    override val elementName: String
        get() = "자격증 $name"
}