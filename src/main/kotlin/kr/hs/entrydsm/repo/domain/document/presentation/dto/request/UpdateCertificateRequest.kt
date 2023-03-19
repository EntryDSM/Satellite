package kr.hs.entrydsm.repo.domain.document.presentation.dto.request

import kr.hs.entrydsm.repo.domain.document.persistence.element.CertificateElement
import org.hibernate.validator.constraints.Length
import java.util.Date
import javax.validation.constraints.NotEmpty

data class UpdateCertificateRequest(

    
    val certificateList: List<CertificateRequest>
) {
    data class CertificateRequest(

        @field:Length(max=30)
        @field:NotEmpty
        val name: String,

        @field:Length(max=30)
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