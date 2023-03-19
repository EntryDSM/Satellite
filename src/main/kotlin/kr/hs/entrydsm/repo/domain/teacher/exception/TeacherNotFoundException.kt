package kr.hs.entrydsm.repo.domain.teacher.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object TeacherNotFoundException : DomainCustomException(
    DomainErrorCode.TEACHER_NOT_FOUND
)