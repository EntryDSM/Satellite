package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateIntroduceRequest
import kr.hs.entrydsm.exit.global.security.SecurityUtil

@UseCase
class UpdateIntroduceUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: UpdateIntroduceRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedIntroduce(document, request)
        )
    }

    private fun documentWithUpdatedIntroduce(
        document: Document,
        request: UpdateIntroduceRequest
    ) : Document {
        return document.updateIntroduce(
            request.toIntroduceElement()
        )
    }

}