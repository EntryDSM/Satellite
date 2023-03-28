package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

@UseCase
class SubmitMyDocumentUseCase(
    private val documentRepository: DocumentRepository
) {
    fun execute() {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        if (document.status != Status.CREATED) {
            throw DocumentIllegalStatusException
        }

        documentRepository.save(
            document.updateStatus(Status.SUBMITTED)
        )
    }
}