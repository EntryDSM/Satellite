package com.example.exit.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

import com.example.exit.global.security.jwt.properties.JwtProperties
import java.util.Date
import java.util.UUID

import com.example.exit.domain.auth.dto.response.TokenResponse
import java.time.LocalDateTime


@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
   // RefreshTokenRepository TODO
) {
    companion object {
        private const val ACCESS_KEY = "access"
        private const val REFRESH_KEY = "refresh"
        private const val TYPE_CLAIM = "typ"
    }

    fun generateBothToken(userId: UUID): TokenResponse {
        return TokenResponse(
            accessToken = this.generateAccessToken(userId),
            accessExpiredAt = LocalDateTime.now().withNano(0).plusSeconds(jwtProperties.accessExp.toLong()),
            refreshToken = this.generateRefreshToken(userId),
            refreshExpiredAt =  LocalDateTime.now().withNano(0).plusSeconds(jwtProperties.refreshExp.toLong())
        )
    }

    private fun generateRefreshToken(userId: UUID): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretKey)
            .setSubject(userId.toString())
            .claim(TYPE_CLAIM, REFRESH_KEY)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp))
            .compact()
    }

    private fun generateAccessToken(userId: UUID): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setSubject(userId.toString())
            .claim(TYPE_CLAIM, ACCESS_KEY)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp))
            .compact()
    }
}