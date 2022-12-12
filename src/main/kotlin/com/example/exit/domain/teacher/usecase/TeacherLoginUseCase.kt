package com.example.exit.domain.teacher.usecase

import com.example.exit.domain.auth.Authority
import com.example.exit.domain.auth.dto.response.TokenResponse
import com.example.exit.domain.auth.persistence.RefreshToken
import com.example.exit.domain.auth.persistence.repository.RefreshTokenRepository
import com.example.exit.domain.teacher.exception.TeacherNotFoundException
import com.example.exit.domain.teacher.persistence.repository.TeacherRepository
import com.example.exit.domain.teacher.presentation.dto.request.TeacherSignInRequest
import com.example.exit.global.exception.jwt.GlobalPasswordMissMatchedException
import com.example.exit.global.security.jwt.JwtTokenProvider
import com.example.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class TeacherLoginUseCase(
    private val tokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val teacherRepository: TeacherRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val securityProperties: SecurityProperties
) {
    fun execute(request: TeacherSignInRequest): TokenResponse? {

        val teacher = teacherRepository.findByAccountId(request.accountId)
            ?: throw TeacherNotFoundException

        if (!passwordEncoder.matches(request.password, teacher.password)) {
            throw GlobalPasswordMissMatchedException
        }

        val tokenResponse = tokenProvider.generateBothToken(teacher.id, Authority.TEACHER)

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