package kr.hs.entrydsm.satellite.global.security.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshTokenDomain
import kr.hs.entrydsm.satellite.domain.auth.dto.TokenResponse
import kr.hs.entrydsm.satellite.domain.auth.spi.RefreshTokenPort
import kr.hs.entrydsm.satellite.domain.auth.spi.TokenPort
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.REFRESH
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.satellite.global.security.token.properties.SecurityProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class JwtGenerateAdapter(
    private val securityProperties: SecurityProperties,
    private val refreshTokenPort: RefreshTokenPort
) : TokenPort {

    override suspend fun generateBothToken(
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

    private suspend fun generateRefreshToken(userId: UUID, authority: Authority): String {

        val token = Jwts
            .builder()
            .signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString())
            .setHeaderParam(TYPE_CLAIM, REFRESH)
            .claim(ROLE_CLAIM, authority.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.refreshExp * 1000))
            .compact()

        val refreshToken = RefreshTokenDomain(
            id = userId,
            token = token,
            authority = authority,
            timeToLive = securityProperties.refreshExp
        )
        refreshTokenPort.save(refreshToken)

        return token
    }

    private suspend fun generateAccessToken(userId: UUID, authority: Authority): String {
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