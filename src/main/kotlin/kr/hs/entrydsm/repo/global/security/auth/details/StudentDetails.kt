package kr.hs.entrydsm.repo.global.security.auth.details

import kr.hs.entrydsm.repo.domain.student.persistence.Student
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class StudentDetails(
    val student: Student
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT.name))
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