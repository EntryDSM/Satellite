package kr.hs.entrydsm.repo.domain.teacher.usecase

import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.common.exception.PasswordMismatchedException
import kr.hs.entrydsm.repo.domain.teacher.exception.TeacherNotFoundException
import kr.hs.entrydsm.repo.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.repo.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.repo.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.repo.global.security.jwt.properties.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class TeacherLoginUseCase(
    private val jwtGenerator: JwtGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val teacherRepository: TeacherRepository,
    private val refreshTokenRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.RefreshTokenRepository,
    private val securityProperties: SecurityProperties
) {
    fun execute(request: TeacherSignInRequest): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse? {

        val teacher = teacherRepository.findByAccountId(request.accountId)
            ?: throw TeacherNotFoundException

        if (!passwordEncoder.matches(request.password, teacher.password)) {
            throw PasswordMismatchedException
        }

        return jwtGenerator.generateBothToken(teacher.id, Authority.TEACHER).also { response ->
            refreshTokenRepository.save(
                kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken(
                    userId = teacher.id,
                    token = response.refreshToken,
                    timeToLive = securityProperties.refreshExp
                )
            )
        }
    }
}