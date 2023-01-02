package kr.hs.entrydsm.exit.domain.auth.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object VerificationCodeMismatchedException : DomainCustomException(
    DomainErrorCode.VERIFICATION_CODE_MISMATCHED
)