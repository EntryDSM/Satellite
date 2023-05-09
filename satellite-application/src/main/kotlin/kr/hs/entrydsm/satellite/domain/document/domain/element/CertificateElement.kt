package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

class CertificateElement(
    elementId: UUID? = null,
    val name: String,
    val issuingInstitution: String,
    val date: Date
) : AbstractElement(elementId) {

    override val elementName: String
        get() = "자격증 $name"
}