package kr.hs.entrydsm.exit.domain.document.usecase

import DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateProjectRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UpdateProjectUseCase(
    private val documentRepository: DocumentRepository
) {
    @Transactional
    fun execute(request: UpdateProjectRequest) {

        val student = SecurityUtil.getCurrentStudent()

        val document = documentRepository
            .findByIdAndWriterStudentId(request.documentId, student.id) ?: throw DocumentNotFoundException

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