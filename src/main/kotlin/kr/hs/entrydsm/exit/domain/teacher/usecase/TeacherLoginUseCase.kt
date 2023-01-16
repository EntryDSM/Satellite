package kr.hs.entrydsm.exit.domain.teacher.usecase

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.auth.persistence.RefreshToken
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.exception.PasswordMismatchedException
import kr.hs.entrydsm.exit.domain.teacher.exception.TeacherNotFoundException
import kr.hs.entrydsm.exit.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.exit.domain.teacher.presentation.dto.request.TeacherSignInRequest
import kr.hs.entrydsm.exit.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder


@UseCase
class TeacherLoginUseCase(
    private val jwtGenerator: JwtGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val teacherRepository: TeacherRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val securityProperties: SecurityProperties
) {

    fun execute(request: TeacherSignInRequest): TokenResponse? {

        val teacher = teacherRepository.findByAccountId(request.accountId)
            ?: throw TeacherNotFoundException

        if (!passwordEncoder.matches(request.password, teacher.password)) {
            throw PasswordMismatchedException
        }

        val tokenResponse = jwtGenerator.generateBothToken(teacher.id, Authority.TEACHER)

        refreshTokenRepository.save(
            RefreshToken(
                userId = teacher.id,
                token = tokenResponse.refreshToken,
                timeToLive = securityProperties.refreshExp
            )
        )

        return tokenResponse
    }
}