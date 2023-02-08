package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.auth.persistence.RefreshToken
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.exit.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestStudentController(
    private val studentRepository: StudentRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtGenerator: JwtGenerator,
    private val securityProperties: SecurityProperties
) {

    @PostMapping("/student/sign/test")
    fun testSignUpOrIn(@RequestParam email: String): TokenResponse {

        val student = studentRepository.findByEmail(email)
            ?: throw SignUpRequiredRedirection(email)

        val tokenResponse = jwtGenerator.generateBothToken(student.id, Authority.STUDENT)

        refreshTokenRepository.save(
            RefreshToken(
                userId = student.id,
                token = tokenResponse.refreshToken,
                timeToLive = securityProperties.refreshExp
            )
        )

        return tokenResponse
    }
}