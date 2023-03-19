package kr.hs.entrydsm.repo.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.global.exception.InternalServerException
import kr.hs.entrydsm.repo.global.exception.jwt.ExpiredTokenException
import kr.hs.entrydsm.repo.global.exception.jwt.InvalidTokenException
import kr.hs.entrydsm.repo.global.exception.jwt.UnexpectedTokenException
import kr.hs.entrydsm.repo.global.security.auth.details.service.CompanyDetailService
import kr.hs.entrydsm.repo.global.security.auth.details.service.StudentDetailService
import kr.hs.entrydsm.repo.global.security.auth.details.service.TeacherDetailService
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.repo.global.security.jwt.properties.SecurityProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtParser(
    private val securityProperties: SecurityProperties,
    private val teacherDetailService: TeacherDetailService,
    private val studentDetailService: StudentDetailService,
    private val companyDetailService: CompanyDetailService
) {

    fun getAuthentication(token: String): Authentication? {
        val claims = getClaims(token)

        if (claims.header[TYPE_CLAIM] != ACCESS) {
            throw InvalidTokenException
        }

        val userDetails = getDetails(claims.body)

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
                else -> throw InternalServerException
            }
        }
    }

    private fun getDetails(body: Claims): UserDetails {

        val authority = Authority.valueOf(
            body.get(ROLE_CLAIM, String::class.java)
        )

        return when (authority) {
            Authority.STUDENT -> studentDetailService
            Authority.COMPANY -> companyDetailService
            Authority.TEACHER -> teacherDetailService
        }.loadUserByUsername(body.subject)
    }
}