package kr.hs.entrydsm.satellite.global.exception

import kr.hs.entrydsm.satellite.global.error.GlobalCustomException
import kr.hs.entrydsm.satellite.global.error.GlobalErrorCode

object ExpiredTokenException : GlobalCustomException(
    GlobalErrorCode.EXPIRED_TOKEN
)

object InvalidTokenException : GlobalCustomException(
    GlobalErrorCode.INVALID_TOKEN
)

object UnexpectedTokenException : GlobalCustomException(
    GlobalErrorCode.UNEXPECTED_TOKEN
)