package kr.hs.entrydsm.satellite.domain.teacher.usecase

import kr.hs.entrydsm.satellite.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.common.exception.PasswordMismatchedException
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.domain.teacher.exception.TeacherNotFoundException
import kr.hs.entrydsm.satellite.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.satellite.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.satellite.common.security.jwt.JwtGenerator
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class TeacherLoginUseCase(
    private val jwtGenerator: JwtGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val teacherRepository: TeacherRepository
) {
    fun execute(request: TeacherSignInRequest): TokenResponse? {

        val teacher = teacherRepository.findByAccountId(request.accountId)
            ?: throw TeacherNotFoundException

        if (!passwordEncoder.matches(request.password, teacher.password)) {
            throw PasswordMismatchedException
        }

        return jwtGenerator.generateBothToken(teacher.id, Authority.TEACHER)
    }
}