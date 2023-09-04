package kr.hs.entrydsm.satellite.domain.student.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentDomain
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.file.domain.DefaultImages
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student.Companion.checkEmailSuffix
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain
import kr.hs.entrydsm.satellite.domain.student.exception.StudentAlreadyExistException
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.util.*

@UseCase
class StudentSignUpUseCase(
    private val studentPort: StudentPort,
    private val documentPort: DocumentPort,
    private val majorPort: MajorPort,
    private val schoolYearPort: SchoolYearPort,
    private val tokenPort: TokenPort
) {
    suspend fun execute(
        name: String,
        profileImagePath: String?,
        email: String,
        grade: Int,
        classNum: Int,
        number: Int,
        majorId: UUID
    ): TokenResponse {

        checkEmailSuffix(email)

        if (studentPort.existsByEmail(email)) {
            println(studentPort.queryByEmail(email))
            throw StudentAlreadyExistException
        }

        val major = majorPort.queryById(majorId) ?: throw MajorNotFoundException

        val student = studentPort.save(
            StudentDomain(
                email = email,
                name = name,
                grade = grade,
                classNum = classNum,
                number = number,
                profileImagePath = profileImagePath ?: DefaultImages.USER_PROFILE
            )
        )

        documentPort.save(
            DocumentDomain(
                writer = WriterInfoElement(student, major),
                year = schoolYearPort.getSchoolYear().year,
                status = DocumentStatus.CREATED
            )
        )

        return tokenPort.generateBothToken(student.id, Authority.STUDENT)
    }
}