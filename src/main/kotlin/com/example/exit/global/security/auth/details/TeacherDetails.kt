package com.example.exit.global.security.auth.details

import com.example.exit.domain.auth.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class TeacherDetails(
    private val teacherId: UUID
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(Authority.TEACHER.name))
    }

    override fun getPassword(): String? =  null;

    override fun getUsername(): String {
        return teacherId.toString()
    }

    override fun isAccountNonExpired(): Boolean = true;

    override fun isAccountNonLocked(): Boolean = true;

    override fun isCredentialsNonExpired(): Boolean = true;

    override fun isEnabled(): Boolean = true;
}