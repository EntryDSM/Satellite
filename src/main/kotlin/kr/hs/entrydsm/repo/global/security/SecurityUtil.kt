package kr.hs.entrydsm.repo.global.security

import kr.hs.entrydsm.repo.domain.auth.constant.Authority
import kr.hs.entrydsm.repo.domain.company.persistence.Company
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.teacher.persistence.Teacher
import kr.hs.entrydsm.repo.global.security.auth.details.CompanyDetails
import kr.hs.entrydsm.repo.global.security.auth.details.StudentDetails
import kr.hs.entrydsm.repo.global.security.auth.details.TeacherDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


object SecurityUtil {

    fun getCurrentUserAuthority(): kr.hs.entrydsm.repo.domain.auth.constant.Authority {

        val authorities = SecurityContextHolder.getContext().authentication.authorities
        return kr.hs.entrydsm.repo.domain.auth.constant.Authority.valueOf(authorities.toList()[0].authority)
    }

    fun getCurrentUserId(): UUID {

        val id = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return UUID.fromString(id)
    }

    fun getCurrentStudent(): Student =
        (SecurityContextHolder.getContext().authentication.principal as StudentDetails).student

    fun getCurrentTeacher(): Teacher =
        (SecurityContextHolder.getContext().authentication.principal as TeacherDetails).teacher

    fun getCurrentCompany(): Company =
        (SecurityContextHolder.getContext().authentication.principal as CompanyDetails).company
}