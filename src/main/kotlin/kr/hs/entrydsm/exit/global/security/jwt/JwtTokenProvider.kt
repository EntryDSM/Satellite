package kr.hs.entrydsm.exit.global.security.jwt

import kr.hs.entrydsm.exit.domain.auth.Authority
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

import kr.hs.entrydsm.exit.global.security.jwt.properties.SecurityProperties
import java.util.Date
import java.util.UUID

import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.REFRESH
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.exit.global.security.jwt.properties.JwtConstants.TYPE_CLAIM
import java.time.LocalDateTime


@Component
class JwtTokenProvider(
    private val securityProperties: SecurityProperties
) {

    fun generateBothToken(userId: UUID, auth: Authority): TokenResponse {
        return TokenResponse(
            accessToken = this.generateAccessToken(userId, auth),
            accessExpiredAt = LocalDateTime.now().withNano(0).plusSeconds(securityProperties.accessExp.toLong()),
            refreshToken = this.generateRefreshToken(userId),
            refreshExpiredAt =  LocalDateTime.now().withNano(0).plusSeconds(securityProperties.refreshExp.toLong())
        )
    }

    private fun generateRefreshToken(userId: UUID): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString())
            .setHeaderParam(TYPE_CLAIM, REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp))
            .compact()
    }

    private fun generateAccessToken(userId: UUID, auth: Authority): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString())
            .setHeaderParam(TYPE_CLAIM, ACCESS)
            .claim(ROLE_CLAIM, auth.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp))
            .compact()
    }
}