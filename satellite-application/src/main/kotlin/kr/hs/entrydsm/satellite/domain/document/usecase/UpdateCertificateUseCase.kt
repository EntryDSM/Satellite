package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateCertificateRequest
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

@UseCase
class UpdateCertificateUseCase(
    private val documentRepository: DocumentRepository
) {
    fun execute(request: UpdateCertificateRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedCertificate(document, request)
        )
    }

    private fun documentWithUpdatedCertificate(
        document: Document,
        request: UpdateCertificateRequest
    ): Document {
        return document.updateCertificateList(
            request.certificateList.map {
                it.toCertificateElement()
            }.toMutableList()
        )
    }
}