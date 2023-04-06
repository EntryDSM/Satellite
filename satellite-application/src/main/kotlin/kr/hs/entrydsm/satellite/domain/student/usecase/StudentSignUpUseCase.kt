package kr.hs.entrydsm.satellite.domain.student.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.domain.file.domain.DefaultImage
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.domain.Student.Companion.checkEmailSuffix
import kr.hs.entrydsm.satellite.domain.student.exception.StudentAlreadyExistException
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

@UseCase
class StudentSignUpUseCase(
    private val studentPort: StudentPort,
    private val tokenPort: TokenPort
) {
    fun execute(
        name: String,
        profileImagePath: String?,
        email: String,
        grade: Int,
        classNum: Int,
        number: Int,
    ): TokenResponse {

        checkEmailSuffix(email)

        if (studentPort.existsByEmail(email)) {
            throw StudentAlreadyExistException
        }

        val student = studentPort.save(
            Student(
                email = email,
                name = name,
                grade = grade,
                classNum = classNum,
                number = number,
                profileImagePath = profileImagePath ?: DefaultImage.USER_PROFILE
            )
        )

        return tokenPort.generateBothToken(student.id, Authority.STUDENT)
    }
}