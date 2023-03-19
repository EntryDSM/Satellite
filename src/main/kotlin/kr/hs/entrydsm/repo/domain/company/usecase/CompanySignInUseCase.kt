package kr.hs.entrydsm.repo.domain.company.usecase

import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.common.exception.PasswordMismatchedException
import kr.hs.entrydsm.repo.domain.company.exception.CompanyInitRequiredException
import kr.hs.entrydsm.repo.domain.company.exception.CompanyNotFoundException
import kr.hs.entrydsm.repo.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignInRequest
import kr.hs.entrydsm.repo.global.security.jwt.JwtGenerator
import kr.hs.entrydsm.repo.global.security.jwt.properties.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class CompanySignInUseCase(
    private val companyRepository: CompanyRepository,
    private val refreshTokenRepository: kr.hs.entrydsm.repo.domain.auth.persistence.repository.RefreshTokenRepository,
    private val jwtGenerator: JwtGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val securityProperties: SecurityProperties
) {

    fun execute(request: CompanySignInRequest): kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse {

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
                kr.hs.entrydsm.repo.domain.auth.persistence.RefreshToken(
                    userId = company.id,
                    token = response.refreshToken,
                    timeToLive = securityProperties.refreshExp
                )
            )
        }
    }
}