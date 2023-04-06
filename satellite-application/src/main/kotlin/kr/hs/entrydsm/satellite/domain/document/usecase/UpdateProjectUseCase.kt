package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.ProjectRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

@UseCase
class UpdateProjectUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort
) {
    fun execute(projectList: List<ProjectRequest>) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentPort.save(
            documentWithUpdatedProject(document, projectList)
        )
    }

    private fun documentWithUpdatedProject(
        document: Document,
        projectList: List<ProjectRequest>
    ): Document {
        return document.copy(
            projectList = projectList.map { it.toProjectElement() }.toMutableList()
        )
    }
}