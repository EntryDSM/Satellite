package kr.hs.entrydsm.exit.domain.auth.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object AlreadyVerifiedException : DomainCustomException(
    DomainErrorCode.ALREADY_VERIFIED
)