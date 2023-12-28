package kr.hs.entrydsm.satellite.global.security.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.reactive.awaitFirst
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.global.exception.ExpiredTokenException
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import kr.hs.entrydsm.satellite.global.exception.InvalidTokenException
import kr.hs.entrydsm.satellite.global.exception.UnexpectedTokenException
import kr.hs.entrydsm.satellite.global.security.auth.details.service.StudentDetailService
import kr.hs.entrydsm.satellite.global.security.auth.details.service.TeacherDetailService
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.satellite.global.security.token.properties.SecurityProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtParser(
    private val securityProperties: SecurityProperties,
    private val teacherDetailService: TeacherDetailService,
    private val studentDetailService: StudentDetailService
) {

    suspend fun getAuthentication(token: String): Authentication? {
        val claims = getClaims(token)

        if (claims.header[TYPE_CLAIM] != ACCESS) {
            throw InvalidTokenException
        }

        val userDetails = try {
            getDetails(claims.body)
        } catch (e: Exception) {
            return null
        }

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parser()
                .setSigningKey(securityProperties.secretKey)
                .parseClaimsJws(token)
        } catch (e: Exception) {
            when (e) {
                is InvalidClaimException -> throw InvalidTokenException
                is ExpiredJwtException -> throw ExpiredTokenException
                is JwtException -> throw UnexpectedTokenException
                else -> throw InternalServerError
            }
        }
    }

    private suspend fun getDetails(body: Claims): UserDetails {

        val authority = Authority.valueOf(
            body.get(ROLE_CLAIM, String::class.java)
        )

        return when (authority) {
            Authority.STUDENT -> studentDetailService
            Authority.TEACHER -> teacherDetailService
        }.findByUsername(body.subject).awaitFirst()
    }
}