package com.example.exit.global.security.jwt

import com.example.exit.global.security.auth.details.service.CompanyDetailService
import com.example.exit.global.security.auth.details.service.StudentDetailService
import com.example.exit.global.security.auth.details.service.TeacherDetailService
import com.example.exit.global.security.jwt.properties.JwtProperties
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtTokenParser(
    private val jwtProperties: JwtProperties,
    private val teacherDetailService: TeacherDetailService,
    private val studentDetailService: StudentDetailService,
    private val companyDetailService: CompanyDetailService
) {

    fun getAuthentication(token: String): Authentication? {
        val claims = getClaims(token);

        return null;
    }

    private fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parseClaimsJws(token);

        // TRY_CATCH 예외처리 TODO
    }

    private fun getDetails(body: Claims): UserDetails {
        TODO("DetailService 구현하고 하기")
    }

}