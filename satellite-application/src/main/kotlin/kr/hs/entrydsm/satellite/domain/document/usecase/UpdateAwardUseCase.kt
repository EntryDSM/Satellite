package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.AwardRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

@UseCase
class UpdateAwardUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort
) {
    fun execute(awardList: List<AwardRequest>) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        documentPort.save(
            documentWithUpdatedAward(document, awardList)
        )
    }

    private fun documentWithUpdatedAward(
        document: Document,
        awardList: List<AwardRequest>
    ): Document {
        return document.copy(
            awardList = awardList.map { it.toAwardElement() }.toMutableList()
        )
    }
}