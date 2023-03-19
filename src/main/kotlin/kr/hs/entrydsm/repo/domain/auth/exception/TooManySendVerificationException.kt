package kr.hs.entrydsm.repo.domain.auth.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object TooManySendVerificationException : DomainCustomException(
    DomainErrorCode.TOO_MANY_SEND_VERIFICATION_CODE
)