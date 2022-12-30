package kr.hs.entrydsm.exit.domain.common.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException
import kr.hs.entrydsm.exit.global.error.GlobalErrorCode
import kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException

object PasswordMissMatchedException : DomainCustomException(
    DomainErrorCode.PASSWORD_MISS_MATCHED
)