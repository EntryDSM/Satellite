package com.example.exit.global.security.jwt

import com.example.exit.domain.auth.Authority
import com.example.exit.global.exception.InternalServerException
import com.example.exit.global.exception.jwt.ExpiredTokenException
import com.example.exit.global.security.auth.details.service.CompanyDetailService
import com.example.exit.global.security.auth.details.service.StudentDetailService
import com.example.exit.global.security.auth.details.service.TeacherDetailService
import com.example.exit.global.security.jwt.properties.JwtProperties.ACCESS
import com.example.exit.global.security.jwt.properties.SecurityProperties
import com.example.exit.global.exception.jwt.InvalidClaimException
import com.example.exit.global.exception.jwt.InvalidTokenException
import com.example.exit.global.exception.jwt.UnexpectedTokenException
import com.example.exit.global.security.jwt.properties.JwtProperties.ROLE_CLAIM
import com.example.exit.global.security.jwt.properties.JwtProperties.TYPE_CLAIM
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
            when (e){
                is InvalidClaimException -> throw InvalidClaimException
                is ExpiredJwtException -> throw ExpiredTokenException
                is JwtException -> throw UnexpectedTokenException
                else -> throw InternalServerException
            }
        }
    };

    private fun getDetails(body: Claims): UserDetails {
        val authority = body.get(ROLE_CLAIM, String::class.java)

        return when (authority) {
            Authority.STUDENT.name -> studentDetailService.loadUserByUsername(body.id)
            Authority.COMPANY.name -> companyDetailService.loadUserByUsername(body.id)
            Authority.TEACHER.name -> teacherDetailService.loadUserByUsername(body.id)
            else -> throw InternalServerException
        }
    }

}