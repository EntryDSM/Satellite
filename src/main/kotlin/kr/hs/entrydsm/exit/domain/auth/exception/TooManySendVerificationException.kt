package kr.hs.entrydsm.exit.domain.auth.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object TooManySendVerificationException : DomainCustomException(
    DomainErrorCode.TOO_MANY_SEND_VERIFICATION_CODE
)