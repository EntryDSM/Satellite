package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.UpdateProfileImageRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort


@UseCase
class UpdateProfileImageUseCase(
    private val securityPort: SecurityPort,
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort
) {
    suspend fun execute(request: UpdateProfileImageRequest) {

        val student = securityPort.getCurrentStudent()

        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException
        documentPort.save(
            document.updateElement(
                writer = document.writer
                    .apply { profileImagePath = request.profileImagePath }
            )
        )

        studentPort.save(
            student.changeProfileImagePath(request.profileImagePath)
        )
    }
}