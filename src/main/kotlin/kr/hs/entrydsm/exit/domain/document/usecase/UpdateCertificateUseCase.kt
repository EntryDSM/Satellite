package kr.hs.entrydsm.exit.domain.document.usecase

import DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateCertificateRequest

@UseCase
class UpdateCertificateUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: UpdateCertificateRequest) {

        val student = SecurityUtil.getCurrentStudent()

        val document = documentRepository
            .findByIdAndWriterStudentId(request.documentId, student.id) ?: throw DocumentNotFoundException

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