package com.example.exit.global.security.auth.details

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CompanyDetails(

): UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Company Entity 만들기")
    }

    override fun getPassword(): String? =  null;

    override fun getUsername(): String {
        TODO("Company Entity 만들기")
    }

    override fun isAccountNonExpired(): Boolean = true;

    override fun isAccountNonLocked(): Boolean = true;

    override fun isCredentialsNonExpired(): Boolean = true;

    override fun isEnabled(): Boolean = true;

}

