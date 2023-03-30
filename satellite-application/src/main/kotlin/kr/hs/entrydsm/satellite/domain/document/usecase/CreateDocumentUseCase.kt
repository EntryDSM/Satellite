package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.school.spi.SchoolYearPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

@UseCase
class CreateDocumentUseCase(
    private val securityPort: SecurityPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort,
    private val schoolYearPort: SchoolYearPort
) {
    fun execute(majorId: UUID): UUID {

        val student = securityPort.getCurrentStudent()

        if (documentPort.existByWriterStudentId(student.id)) {
            throw DocumentAlreadyExistException
        }

        val major = majorPort.queryById(majorId) ?: throw MajorNotFoundException

        val document = documentPort.save(
            Document(
                writer = WriterInfoElement(student, major),
                year = schoolYearPort.getSchoolYear().year,
                documentStatus = DocumentStatus.CREATED
            )
        )

        return document.id
    }

    private fun studentDocumentIsExist(student: Student): Boolean {
        return documentPort.queryByWriterStudentId(student.id) != null
    }
}