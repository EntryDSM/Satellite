package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.CertificateRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

@UseCase
class UpdateCertificateUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort
) {
    suspend fun execute(requests: List<CertificateRequest>) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentPort.save(
            document.updateElement(
                certificateList = requests.map { it.toCertificateElement() }
            )
        )
    }
}