package kr.hs.entrydsm.satellite.domain.document.domain.element

import java.util.*

data class CertificateElement(
    override val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val issuingInstitution: String,
    val date: Date
) : AbstractElement() {

    override val elementName: String
        get() = "자격증 $name"
}