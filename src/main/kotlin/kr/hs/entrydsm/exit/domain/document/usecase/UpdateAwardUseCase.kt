package kr.hs.entrydsm.exit.domain.document.usecase

import DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateAwardRequest
import javax.transaction.Transactional

@UseCase
class UpdateAwardUseCase (
    private val documentRepository: DocumentRepository
) {

    @Transactional
    fun execute(request: UpdateAwardRequest) {

        val student = SecurityUtil.getCurrentStudent()

        val document = documentRepository
            .findByIdAndWriterStudentId(request.documentId, student.id) ?: throw DocumentNotFoundException

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