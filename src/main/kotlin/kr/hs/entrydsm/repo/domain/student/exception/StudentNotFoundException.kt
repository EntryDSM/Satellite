package kr.hs.entrydsm.repo.domain.student.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object StudentNotFoundException : DomainCustomException(
    DomainErrorCode.STUDENT_NOT_FOUND
)