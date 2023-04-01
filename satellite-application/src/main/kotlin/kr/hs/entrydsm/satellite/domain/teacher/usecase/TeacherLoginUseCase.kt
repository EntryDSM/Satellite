package kr.hs.entrydsm.satellite.domain.teacher.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.common.exception.PasswordMismatchException
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.domain.teacher.exception.TeacherNotFoundException
import kr.hs.entrydsm.satellite.domain.teacher.spi.TeacherPort

@UseCase
class TeacherLoginUseCase(
    private val tokenPort: TokenPort,
    private val securityPort: SecurityPort,
    private val teacherPort: TeacherPort
) {
    fun execute(accountId: String, password: String): TokenResponse? {

        val teacher = teacherPort.queryByAccountId(accountId)
            ?: throw TeacherNotFoundException

        if (!securityPort.encyptMatches(password, teacher.password)) {
            throw PasswordMismatchException
        }

        return tokenPort.generateBothToken(teacher.id, Authority.TEACHER)
    }
}