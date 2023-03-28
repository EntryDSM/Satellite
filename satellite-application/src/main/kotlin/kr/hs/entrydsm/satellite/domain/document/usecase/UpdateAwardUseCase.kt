package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateAwardRequest
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

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