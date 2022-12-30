package kr.hs.entrydsm.exit.global.security.jwt

import io.jsonwebtoken.*
import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.global.exception.GlobalInternalServerException
import kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidTokenException
import kr.hs.entrydsm.exit.global.security.auth.details.service.CompanyDetailService
import kr.hs.entrydsm.exit.global.security.auth.details.service.StudentDetailService
import kr.hs.entrydsm.exit.global.security.auth.details.service.TeacherDetailService
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtTokenParser(
    private val securityProperties: SecurityProperties,
    private val teacherDetailService: TeacherDetailService,
    private val studentDetailService: StudentDetailService,
    private val companyDetailService: CompanyDetailService
) {

    fun getAuthentication(token: String): Authentication? {
        val claims = getClaims(token);

        if (claims.header[TYPE_CLAIM] != ACCESS) {
            throw GlobalInvalidTokenException
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
            when (e){
                is kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidClaimException -> throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidClaimException
                is ExpiredJwtException -> throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalExpiredTokenException
                is JwtException -> throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalUnexpectedTokenException
                else -> throw GlobalInternalServerException
            }
        }
    };

    private fun getDetails(body: Claims): UserDetails {
        val authority = body.get(ROLE_CLAIM, String::class.java)

        return when (authority) {
            Authority.STUDENT.name -> studentDetailService.loadUserByUsername(body.subject)
            Authority.COMPANY.name -> companyDetailService.loadUserByUsername(body.subject)
            Authority.TEACHER.name -> teacherDetailService.loadUserByUsername(body.subject)
            else -> throw GlobalInternalServerException
        }
    }

}