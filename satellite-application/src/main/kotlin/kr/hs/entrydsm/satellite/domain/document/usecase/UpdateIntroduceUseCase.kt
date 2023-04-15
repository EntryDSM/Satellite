package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.IntroduceRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

@UseCase
class UpdateIntroduceUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort
) {
    suspend fun execute(introduce: IntroduceRequest) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentPort.save(
            documentWithUpdatedIntroduce(document, introduce)
        )
    }

    private fun documentWithUpdatedIntroduce(
        document: Document,
        introduce: IntroduceRequest
    ): Document {
        return document.copy(introduce = introduce.toIntroduceElement())
    }
}