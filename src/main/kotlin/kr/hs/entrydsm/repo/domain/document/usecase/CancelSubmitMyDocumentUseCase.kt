package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.exception.IllegalStatusException
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.global.security.SecurityUtil

@UseCase
class CancelSubmitMyDocumentUseCase(
    private val documentRepository: DocumentRepository
) {
    fun execute() {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        if(document.status != Status.SUBMITTED) {
            throw IllegalStatusException
        }

        documentRepository.save(
            document.updateStatus(Status.CREATED)
        )
    }
}