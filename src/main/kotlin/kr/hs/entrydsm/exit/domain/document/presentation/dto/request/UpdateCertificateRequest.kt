package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.CertificateElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.NotEmpty

data class UpdateCertificateRequest(

    @field:NotNull
    val documentId: UUID,

    @field:NotNull
    val certificateList: List<CertificateRequest>
) {
    data class CertificateRequest(

        @field:Length(max=30)
        @field:NotEmpty
        val name: String,

        @field:Length(max=30)
        @field:NotEmpty
        val issuingInstitution: String,

        @field:NotNull
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