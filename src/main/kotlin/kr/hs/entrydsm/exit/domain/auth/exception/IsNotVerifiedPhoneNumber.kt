package kr.hs.entrydsm.exit.domain.auth.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object IsNotVerifiedPhoneNumber : DomainCustomException(
    DomainErrorCode.IS_NOT_VERIFIED_PHONE_NUMBER
)