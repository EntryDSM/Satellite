package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateAwardRequest
import kr.hs.entrydsm.repo.global.security.SecurityUtil

@UseCase
class UpdateAwardUseCase(
    private val documentRepository: DocumentRepository
) {
    fun execute(request: UpdateAwardRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedAward(document, request)
        )
    }

    private fun documentWithUpdatedAward(
        document: Document,
        request: UpdateAwardRequest
    ): Document {
        return document.updateAwardList(
            request.awardList.map {
                it.toAwardElement()
            }.toMutableList()
        )
    }
}