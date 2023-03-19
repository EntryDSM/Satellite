package kr.hs.entrydsm.repo.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.ACCESS
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.REFRESH
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.ROLE_CLAIM
import kr.hs.entrydsm.repo.global.security.jwt.properties.JwtConstants.TYPE_CLAIM
import kr.hs.entrydsm.repo.global.security.jwt.properties.SecurityProperties
import org.springframework.stereotype.Component

@Component
class JwtGenerator(
    private val securityProperties: SecurityProperties,
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

    private fun generateRefreshToken(userId: UUID, auth: Authority): String {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString()).setHeaderParam(TYPE_CLAIM, REFRESH).claim(ROLE_CLAIM, auth.name)
            .setIssuedAt(Date()).setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp * 1000))
            .compact()
    }

    private fun generateAccessToken(userId: UUID, auth: Authority): String {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, securityProperties.secretKey)
            .setSubject(userId.toString()).setHeaderParam(TYPE_CLAIM, ACCESS).claim(ROLE_CLAIM, auth.name)
            .setIssuedAt(Date()).setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp * 1000))
            .compact()
    }
}