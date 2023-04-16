package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentResponse
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort

@ReadOnlyUseCase
class QueryMyDocumentUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val filePort: FilePort
) {
    fun execute(): DocumentResponse {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id)

        val fileBaseUrl = filePort.getFileBaseUrl()
        return DocumentResponse(
            profileImageUrl = fileBaseUrl + student.profileImagePath,
            email = document?.writer?.email ?: student.email,
            name = student.name,
            grade = student.grade,
            classNum = student.classNum,
            number = student.number,
            isExist = document != null,
            status = document?.status,
            majorName = document?.writer?.majorName,
            heading = document?.introduce?.heading,
            introduce = document?.introduce?.introduce
        )
    }
}