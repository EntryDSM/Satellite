package kr.hs.entrydsm.satellite.domain.auth.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object RefreshTokenNotFoundException : DomainCustomException(
    DomainErrorCode.REFRESH_TOKEN_NOT_FOUND
)

object PasswordMismatch : DomainCustomException(
    DomainErrorCode.REFRESH_TOKEN_NOT_FOUND
)