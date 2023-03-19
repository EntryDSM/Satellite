package kr.hs.entrydsm.repo.domain.student.usecase

import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.common.exception.EmailSuffixNotValidException
import kr.hs.entrydsm.repo.domain.file.domain.DefaultImage
import kr.hs.entrydsm.repo.domain.student.exception.StudentAlreadyExistException
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.domain.student.presentation.dto.request.StudentSignUpRequest
import kr.hs.entrydsm.repo.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.repo.global.util.RegexUtil
import java.util.regex.*

@UseCase
class StudentSignUpUseCase(
    private val studentRepository: StudentRepository,
    private val jwtGenerator: JwtGenerator
) {

    fun execute(request: StudentSignUpRequest): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {

        checkEmailSuffix(request.email)

        if (studentRepository.existsByEmail(request.email)) {
            throw StudentAlreadyExistException
        }

        val student = request.run {
            studentRepository.save(
                Student(
                    email = email,
                    name = name,
                    grade = grade,
                    classNum = classNum,
                    number = number,
                    profileImagePath = profileImagePath ?: DefaultImage.USER_PROFILE
                )
            )
        }

        return jwtGenerator.generateBothToken(student.id, kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT)
    }

    private fun checkEmailSuffix(email: String) {
        if (!Pattern.matches(RegexUtil.EMAIL_EXP, email)) {
            throw EmailSuffixNotValidException
        }
    }

}
