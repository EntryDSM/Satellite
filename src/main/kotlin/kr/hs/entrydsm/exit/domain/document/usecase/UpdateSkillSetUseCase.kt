package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateSkillSetRequest
import kr.hs.entrydsm.exit.global.security.SecurityUtil

@UseCase
class UpdateSkillSetUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: UpdateSkillSetRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedSkillSet(document, request)
        )
    }

    private fun documentWithUpdatedSkillSet(
        document: Document,
        request: UpdateSkillSetRequest
    ): Document {
        return document.updateSkillSet(
            request.skillList.toMutableList()
        )
    }
}