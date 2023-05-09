package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import java.util.*

open class CertificateRequest(
    open val elementId: UUID?,
    open val name: String,
    open val issuingInstitution: String,
    open val date: Date
) {
    fun toCertificateElement(): CertificateElement {
        return CertificateElement(
            elementId = elementId,
            name = name,
            issuingInstitution = issuingInstitution,
            date = date
        )
    }
}