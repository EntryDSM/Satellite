package com.example.exit.global.security.auth.details.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class StudentDetailService (
    // private val studentRepository StudentRepository
): UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Student Entity 만들고 진행")
    }
}