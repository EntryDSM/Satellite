package kr.hs.entrydsm.exit.domain.common.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object PasswordMismatchedException : DomainCustomException(
    DomainErrorCode.PASSWORD_MISMATCHED
)