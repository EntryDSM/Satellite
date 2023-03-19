package kr.hs.entrydsm.repo.global.security.auth.details.service

import java.util.UUID
import kr.hs.entrydsm.repo.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.repo.global.exception.jwt.InvalidTokenException
import kr.hs.entrydsm.repo.global.security.auth.details.CompanyDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CompanyDetailService(
    private val companyRepository: CompanyRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        val company = companyRepository.findByIdOrNull(UUID.fromString(username)) ?: throw InvalidTokenException
        return CompanyDetails(company)
    }
}