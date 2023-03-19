package kr.hs.entrydsm.exit.global.security.auth.details

import kr.hs.entrydsm.exit.domain.auth.constant.Authority
import kr.hs.entrydsm.exit.domain.teacher.persistence.Teacher
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class TeacherDetails(
    val teacher: Teacher
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(Authority.TEACHER.name))
    }

    override fun getPassword(): String? =  null

    override fun getUsername(): String {
        return teacher.id.toString()
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}