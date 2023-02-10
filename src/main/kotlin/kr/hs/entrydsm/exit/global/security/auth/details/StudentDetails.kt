package kr.hs.entrydsm.exit.global.security.auth.details

import kr.hs.entrydsm.exit.domain.auth.constant.Authority
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class StudentDetails(
    val student: Student
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(Authority.STUDENT.name))
    }

    override fun getPassword(): String? =  null

    override fun getUsername(): String {
        return student.id.toString()
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}