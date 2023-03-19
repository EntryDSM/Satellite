package kr.hs.entrydsm.repo.domain.student.presentation

import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.repo.global.security.jwt.properties.SecurityProperties
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestStudentController(
    private val studentRepository: StudentRepository,
    private val refreshTokenRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.RefreshTokenRepository,
    private val jwtGenerator: JwtGenerator,
    private val securityProperties: SecurityProperties
) {

    @PostMapping("/student/sign/test")
    fun testSignUpOrIn(@RequestParam email: String): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {

        val student = studentRepository.findByEmail(email)
            ?: throw SignUpRequiredRedirection(email)

        val tokenResponse = jwtGenerator.generateBothToken(student.id, Authority.STUDENT)

        refreshTokenRepository.save(
            kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken(
                userId = student.id,
                token = tokenResponse.refreshToken,
                timeToLive = securityProperties.refreshExp
            )
        )

        return tokenResponse
    }
}