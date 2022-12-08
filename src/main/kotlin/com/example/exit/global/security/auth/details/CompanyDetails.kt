package com.example.exit.global.security.auth.details

import com.example.exit.domain.auth.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class CompanyDetails(
    private val companyId: UUID
): UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(Authority.COMPANY.name))
    }

    override fun getPassword(): String? =  null;

    override fun getUsername(): String {
        return companyId.toString()
    }

    override fun isAccountNonExpired(): Boolean = true;

    override fun isAccountNonLocked(): Boolean = true;

    override fun isCredentialsNonExpired(): Boolean = true;

    override fun isEnabled(): Boolean = true;

}

