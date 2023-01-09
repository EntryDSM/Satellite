package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateProjectRequest
import kr.hs.entrydsm.exit.global.security.SecurityUtil

@UseCase
class UpdateProjectUseCase(
    private val documentRepository: DocumentRepository
) {

    fun execute(request: UpdateProjectRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedProject(document, request)
        )
    }

    private fun documentWithUpdatedProject(
        document: Document,
        request: UpdateProjectRequest
    ): Document {
        return document.updateProject(
            request.projectList.map {
                it.toProjectElement()
            }.toMutableList()
        )
    }
}