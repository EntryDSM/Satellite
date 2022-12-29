package kr.hs.entrydsm.exit.domain.auth.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object PhoneNumberVerificationCodeNotFoundException : DomainCustomException(
    DomainErrorCode.PHONE_NUMBER_VERIFICATION_CODE_NOT_FOUND
)