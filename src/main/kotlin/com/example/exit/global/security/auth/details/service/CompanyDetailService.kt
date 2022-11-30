package com.example.exit.global.security.auth.details.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CompanyDetailService (
    // private val companyRepository: CompanyRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Company Entity 만들고 진행")
    }

}