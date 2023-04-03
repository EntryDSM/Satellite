package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.document.dto.CertificateRequest
import org.hibernate.validator.constraints.Length
import java.util.*
import javax.validation.constraints.NotEmpty

data class UpdateCertificateWebRequest(
    val certificateList: List<CertificateWebRequest>
) {
    data class CertificateWebRequest(

        @field:Length(max = 30)
        @field:NotEmpty
        override val name: String,

        @field:Length(max = 30)
        @field:NotEmpty
        override val issuingInstitution: String,

        override val date: Date
    ) : CertificateRequest(name, issuingInstitution, date)
}