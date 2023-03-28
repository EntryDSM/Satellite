package kr.hs.entrydsm.satellite.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.persistence.RefreshToken
import kr.hs.entrydsm.satellite.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.satellite.domain.student.persistence.Authority
import kr.hs.entrydsm.satellite.common.security.jwt.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.satellite.common.security.jwt.properties.JwtConstants.REFRESH
import kr.hs.entrydsm.satellite.common.security.jwt.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.satellite.common.security.jwt.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.satellite.common.security.jwt.properties.SecurityProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class JwtGenerator(
    private val securityProperties: SecurityProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun generateBothToken(
        userId: UUID,
        auth: Authority,
    ): TokenResponse {
        return TokenResponse(
            accessToken = this.generateAccessToken(userId, auth),
            accessExpiredAt = nowPlusSeconds(securityProperties.accessExp),
            refreshToken = this.generateRefreshToken(userId, auth),
            refreshExpiredAt = nowPlusSeconds(securityProperties.refreshExp)
        )
    }

    private fun nowPlusSeconds(exp: Long): LocalDateTime = LocalDateTime.now().withNano(0).plusSeconds(exp)

    private fun generateRefreshToken(userId: UUID, authority: Authority): String {

        val token = Jwts
            .builder()
            .signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString())
            .setHeaderParam(TYPE_CLAIM, REFRESH)
            .claim(ROLE_CLAIM, authority.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.refreshExp * 1000))
            .compact()

        val refreshToken = RefreshToken(
            id = userId,
            token = token,
            authority = authority,
            timeToLive = securityProperties.refreshExp
        )
        refreshTokenRepository.save(refreshToken)

        return token
    }

    private fun generateAccessToken(userId: UUID, authority: Authority): String {
        return Jwts
            .builder()
            .signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString())
            .setHeaderParam(TYPE_CLAIM, ACCESS)
            .claim(ROLE_CLAIM, authority.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp * 1000))
            .compact()
    }
}