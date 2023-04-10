package kr.hs.entrydsm.satellite.domain.document.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class UpdateWriterInfoUseCase(
    private val securityPort: SecurityPort,
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort,
    private val schoolYearPort: SchoolYearPort
) {
    fun execute(writerInfo: WriterInfoRequest) {

        val student = securityPort.getCurrentStudent()

        val major = majorPort.queryById(writerInfo.majorId) ?: throw MajorNotFoundException

        documentPort.queryByWriterStudentId(student.id)?.let {
            updateDocument(it, student, major, writerInfo)
        } ?: createDocument(student, major, writerInfo)

        studentPort.save(
            writerInfo.toStudent(student)
        )
    }

    private fun createDocument(
        student: Student,
        major: Major,
        writerInfo: WriterInfoRequest
    ) = documentPort.save(
        Document(
            writer = writerInfo.toElement(student, major),
            year = schoolYearPort.getSchoolYear().year,
            status = DocumentStatus.CREATED
        )
    )

    private fun updateDocument(
        document: Document,
        student: Student,
        major: Major,
        writerInfo: WriterInfoRequest
    ) = documentPort.save(
        document.copy(
            writer = writerInfo.toElement(student, major)
        )
    )
}