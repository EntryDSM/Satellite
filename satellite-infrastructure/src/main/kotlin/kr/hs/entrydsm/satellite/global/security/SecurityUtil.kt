package kr.hs.entrydsm.satellite.global.security

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.student.persistence.StudentJpaEntity
import kr.hs.entrydsm.satellite.domain.teacher.persistence.TeacherJpaEntity
import kr.hs.entrydsm.satellite.common.security.auth.details.StudentDetails
import kr.hs.entrydsm.satellite.common.security.auth.details.TeacherDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtil {

    fun getCurrentUserAuthority(): Authority {
        val authorities = SecurityContextHolder.getContext().authentication.authorities
        return Authority.valueOf(authorities.toList()[0].authority)
    }

    fun getCurrentUserId(): UUID {
        val id = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return UUID.fromString(id)
    }

    fun getCurrentStudent(): StudentJpaEntity =
        (SecurityContextHolder.getContext().authentication.principal as StudentDetails).student

    fun getCurrentTeacher(): TeacherJpaEntity =
        (SecurityContextHolder.getContext().authentication.principal as TeacherDetails).teacher
}