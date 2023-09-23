package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class UpdateWriterInfoUseCase(
    private val securityPort: SecurityPort,
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort
) {
    suspend fun execute(request: WriterInfoRequest) { // TODO: skill set api와 병합

        val student = securityPort.getCurrentStudent()

        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException
        documentPort.save(
            document.run {
                val major = request.majorId?.let { majorPort.queryById(it) }
                val writer = major?.let {
                    request.toElement(
                        elementId = writer.elementId,
                        skillSet = request.skillSet ?: writer.skillSet ?: skillSet,
                        student = student,
                        major = major
                    )
                } ?: request.toElement(
                    elementId = writer.elementId,
                    skillSet = request.skillSet ?: writer.skillSet ?: skillSet,
                    student = student,
                    majorName = document.writer.name,
                    majorId = document.writer.majorId
                )
                updateElement(writer)
            }
        )

        studentPort.save(
            request.toStudent(student)
        )
    }
}