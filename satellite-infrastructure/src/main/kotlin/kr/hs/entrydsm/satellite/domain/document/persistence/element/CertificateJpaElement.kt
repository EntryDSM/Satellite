package kr.hs.entrydsm.satellite.domain.document.persistence.element

import com.querydsl.core.annotations.QueryEmbeddable
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import java.util.*

@QueryEmbeddable
class CertificateJpaElement(
    elementId: UUID,
    name: String,
    issuingInstitution: String,
    date: Date
) : CertificateElement(elementId, name, issuingInstitution, date) {
    companion object {
        fun of(certificateElement: CertificateElement) = certificateElement.run {
            CertificateJpaElement(elementId, name, issuingInstitution, date)
        }
    }
}