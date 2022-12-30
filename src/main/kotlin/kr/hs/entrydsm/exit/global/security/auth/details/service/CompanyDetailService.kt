package kr.hs.entrydsm.exit.global.security.auth.details.service

import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.global.security.auth.details.CompanyDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CompanyDetailService (
     private val companyRepository: CompanyRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val company = companyRepository.findByIdOrNull(UUID.fromString(username)) ?: throw kr.hs.entrydsm.exit.global.exception.jwt.GlobalInvalidTokenException

        return CompanyDetails(company)
    }

}