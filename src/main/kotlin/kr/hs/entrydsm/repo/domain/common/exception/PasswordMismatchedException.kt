package kr.hs.entrydsm.repo.domain.common.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object PasswordMismatchedException : DomainCustomException(
    DomainErrorCode.PASSWORD_MISMATCHED
)