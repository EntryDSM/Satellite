package kr.hs.entrydsm.satellite.domain.student.presentation

import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.student.exception.SignUpRequiredRedirection
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.common.security.jwt.JwtGenerator
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