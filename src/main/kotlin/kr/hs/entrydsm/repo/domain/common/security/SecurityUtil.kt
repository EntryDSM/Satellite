package kr.hs.entrydsm.repo.domain.common.security

import kr.hs.entrydsm.repo.domain.company.persistence.Company
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.teacher.persistence.Teacher
import kr.hs.entrydsm.repo.global.security.auth.details.CompanyDetails
import kr.hs.entrydsm.repo.global.security.auth.details.StudentDetails
import kr.hs.entrydsm.repo.global.security.auth.details.TeacherDetails
import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID


object SecurityUtil {
    fun getCurrentUserId(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    }

    fun getCurrentStudent(): Student =
        (SecurityContextHolder.getContext().authentication.principal as StudentDetails).student

    fun getCurrentTeacher(): Teacher =
        (SecurityContextHolder.getContext().authentication.principal as TeacherDetails).teacher

    fun getCurrentCompany(): Company =
        (SecurityContextHolder.getContext().authentication.principal as CompanyDetails).company
}