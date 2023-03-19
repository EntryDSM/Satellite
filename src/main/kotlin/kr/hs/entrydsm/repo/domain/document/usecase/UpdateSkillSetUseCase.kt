package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateSkillSetRequest
import kr.hs.entrydsm.repo.global.security.SecurityUtil

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