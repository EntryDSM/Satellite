package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class UpdateWriterInfoUseCase(
    private val securityPort: SecurityPort,
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort
) {
    suspend fun execute(request: WriterInfoRequest) {

        val student = securityPort.getCurrentStudent()

        val major = majorPort.queryById(request.majorId) ?: throw MajorNotFoundException

        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException
        documentPort.save(
            document.updateElement(writer = request.toElement(student, major))
        )

        studentPort.save(
            request.toStudent(student)
        )
    }
}