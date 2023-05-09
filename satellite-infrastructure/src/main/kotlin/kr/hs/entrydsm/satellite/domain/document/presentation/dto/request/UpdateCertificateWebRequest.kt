package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.domain.document.dto.CertificateRequest
import org.hibernate.validator.constraints.Length
import java.util.*

data class UpdateCertificateWebRequest(
    @field:Size(max = 3)
    val certificateList: List<CertificateWebRequest>
) {
    data class CertificateWebRequest(

        override val elementId: UUID?,

        @field:Length(max = 30)
        @field:NotEmpty
        override val name: String,

        @field:Length(max = 30)
        @field:NotEmpty
        override val issuingInstitution: String,

        override val date: Date
    ) : CertificateRequest(elementId, name, issuingInstitution, date)
}