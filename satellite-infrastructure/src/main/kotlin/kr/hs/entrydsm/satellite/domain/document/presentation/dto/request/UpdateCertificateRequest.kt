package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import java.util.Date
import javax.validation.constraints.NotEmpty
import kr.hs.entrydsm.satellite.domain.document.domain.element.CertificateElement
import org.hibernate.validator.constraints.Length

data class UpdateCertificateRequest(

    val certificateList: List<CertificateRequest>
) {
    data class CertificateRequest(

        @field:Length(max = 30)
        @field:NotEmpty
        val name: String,

        @field:Length(max = 30)
        @field:NotEmpty
        val issuingInstitution: String,

        val date: Date
    ) {
        fun toCertificateElement(): CertificateElement {
            return CertificateElement(
                name = name,
                issuingInstitution = issuingInstitution,
                date = date
            )
        }
    }
}