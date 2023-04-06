package kr.hs.entrydsm.satellite.global.security

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.global.security.auth.details.StudentDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@Adapter
class SecurityAdapter(
    private val passwordEncoder: PasswordEncoder
) : SecurityPort {

    override fun getCurrentStudent() =
        (SecurityContextHolder.getContext().authentication.principal as StudentDetails).student

    override fun getCurrentUserAuthority(): Authority {
        val authorities = SecurityContextHolder.getContext().authentication.authorities
        return Authority.valueOf(authorities.toList()[0].authority)
    }

    override fun getCurrentUserId(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    }

    override fun encyptMatches(rawString: String, encryptedString: String) =
        passwordEncoder.matches(rawString, encryptedString)
}