package kr.hs.entrydsm.exit.domain.student.presentation

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.exit.global.security.jwt.JwtGenerator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestStudentController(
    private val studentRepository: StudentRepository,
    private val jwtGenerator: JwtGenerator
) {

    @PostMapping("/student/sign/test")
    fun testSignUpOrIn(@RequestParam email: String): TokenResponse {

        val student = studentRepository.findByEmail(email)
            ?: throw SignUpRequiredRedirection(email)

        return jwtGenerator.generateBothToken(student.id, Authority.STUDENT)
    }
}