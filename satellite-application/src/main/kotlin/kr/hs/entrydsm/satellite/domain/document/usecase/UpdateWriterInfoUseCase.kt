package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class UpdateWriterInfoUseCase(
    private val securityPort: SecurityPort,
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort
) {
    fun execute(writerInfo: WriterInfoRequest) {

        val student = securityPort.getCurrentStudent()
        val document = documentPort.queryByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val savedStudent = studentPort.save(
            studentWithUpdatedInfo(student, writerInfo)
        )

        documentPort.save(
            documentWithUpdatedWriterInfo(document, savedStudent, writerInfo)
        )
    }

    private fun studentWithUpdatedInfo(student: Student, writerInfo: WriterInfoRequest) =
        writerInfo.run {
            student.copy(
                grade = grade,
                classNum = classNum,
                number = number,
                profileImagePath = profileImagePath
            )
        }

    private fun documentWithUpdatedWriterInfo(
        document: Document,
        student: Student,
        writerInfo: WriterInfoRequest
    ): Document {

        val major = majorPort.queryById(writerInfo.majorId) ?: throw MajorNotFoundException

        return writerInfo.run {
            document.copy(
                writer = WriterInfoElement(
                    student = student,
                    major = major
                )
            )
        }
    }
}