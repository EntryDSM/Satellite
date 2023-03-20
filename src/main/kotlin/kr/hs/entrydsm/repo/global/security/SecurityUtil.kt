package kr.hs.entrydsm.repo.global.security

import java.util.UUID
import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.teacher.persistence.Teacher
import kr.hs.entrydsm.repo.global.security.auth.details.StudentDetails
import kr.hs.entrydsm.repo.global.security.auth.details.TeacherDetails
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

    fun getCurrentStudent(): Student =
        (SecurityContextHolder.getContext().authentication.principal as StudentDetails).student

    fun getCurrentTeacher(): Teacher =
        (SecurityContextHolder.getContext().authentication.principal as TeacherDetails).teacher
}