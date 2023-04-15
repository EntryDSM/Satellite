package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

@UseCase
class UpdateSkillSetUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort
) {
    suspend fun execute(skillSet: List<String>) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentPort.save(
            documentWithUpdatedSkillSet(document, skillSet)
        )
    }

    private fun documentWithUpdatedSkillSet(
        document: Document,
        skillSet: List<String>
    ): Document {
        return document.copy(
            skillSet = skillSet.toMutableList()
        )
    }
}