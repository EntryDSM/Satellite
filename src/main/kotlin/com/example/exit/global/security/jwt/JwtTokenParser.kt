package com.example.exit.global.security.jwt

import com.example.exit.domain.auth.Authority
import com.example.exit.global.exception.GlobalInternalServerException
import com.example.exit.global.exception.jwt.GlobalExpiredTokenException
import com.example.exit.global.security.auth.details.service.CompanyDetailService
import com.example.exit.global.security.auth.details.service.StudentDetailService
import com.example.exit.global.security.auth.details.service.TeacherDetailService
import com.example.exit.global.security.jwt.properties.JwtConstants.ACCESS
import com.example.exit.global.security.jwt.properties.SecurityProperties
import com.example.exit.global.exception.jwt.GlobalInvalidClaimException
import com.example.exit.global.exception.jwt.GlobalInvalidTokenException
import com.example.exit.global.exception.jwt.GlobalUnexpectedTokenException
import com.example.exit.global.security.jwt.properties.JwtConstants.ROLE_CLAIM
import com.example.exit.global.security.jwt.properties.JwtConstants.TYPE_CLAIM
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
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
                is GlobalInvalidClaimException -> throw GlobalInvalidClaimException
                is ExpiredJwtException -> throw GlobalExpiredTokenException
                is JwtException -> throw GlobalUnexpectedTokenException
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