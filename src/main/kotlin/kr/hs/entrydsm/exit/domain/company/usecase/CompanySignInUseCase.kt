package kr.hs.entrydsm.exit.domain.company.usecase

import kr.hs.entrydsm.exit.domain.auth.constant.Authority
import kr.hs.entrydsm.exit.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.exit.domain.auth.persistence.RefreshToken
import kr.hs.entrydsm.exit.domain.auth.persistence.repository.RefreshTokenRepository
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.exception.PasswordMismatchedException
import kr.hs.entrydsm.exit.domain.company.exception.CompanyInitRequiredException
import kr.hs.entrydsm.exit.domain.company.exception.CompanyNotFoundException
import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanySignInRequest
import kr.hs.entrydsm.exit.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class CompanySignInUseCase(
    private val companyRepository: CompanyRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtGenerator: JwtGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val securityProperties: SecurityProperties
) {

    fun execute(request: CompanySignInRequest): TokenResponse {

        val company = companyRepository.findByEmail(request.email)
            ?: throw CompanyNotFoundException

        if (!company.isInitialized) {
            throw CompanyInitRequiredException
        }

        if (!passwordEncoder.matches(request.password, company.password)) {
            throw PasswordMismatchedException
        }

        return jwtGenerator.generateBothToken(company.id, Authority.COMPANY).also { response ->
            refreshTokenRepository.save(
                RefreshToken(
                    userId = company.id,
                    token = response.refreshToken,
                    timeToLive = securityProperties.refreshExp
                )
            )
        }
    }
}