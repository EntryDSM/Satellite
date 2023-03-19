package kr.hs.entrydsm.repo.domain.student.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object StudentAlreadyExistException : DomainCustomException(
    DomainErrorCode.STUDENT_ALREADY_EXIST
)