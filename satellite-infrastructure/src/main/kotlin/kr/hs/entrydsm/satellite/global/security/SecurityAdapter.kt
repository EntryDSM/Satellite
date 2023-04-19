package kr.hs.entrydsm.satellite.global.security

import kotlinx.coroutines.reactor.awaitSingle
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.global.security.auth.details.StudentDetails
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@Adapter
class SecurityAdapter(
    private val passwordEncoder: PasswordEncoder
) : SecurityPort {

    override suspend fun getCurrentStudent() =
        (ReactiveSecurityContextHolder.getContext().awaitSingle().authentication.principal as StudentDetails).student

    override suspend fun getCurrentUserAuthority(): Authority {
        val authorities = ReactiveSecurityContextHolder.getContext().awaitSingle().authentication.authorities
        return Authority.valueOf(authorities.toList()[0].authority)
    }

    override suspend fun getCurrentUserId(): UUID {
        return UUID.fromString(ReactiveSecurityContextHolder.getContext().awaitSingle().authentication.name)
    }

    override fun encyptMatches(rawString: String, encryptedString: String) =
        passwordEncoder.matches(rawString, encryptedString)
}