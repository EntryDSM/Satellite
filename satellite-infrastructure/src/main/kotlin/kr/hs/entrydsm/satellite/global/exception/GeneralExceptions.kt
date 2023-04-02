package kr.hs.entrydsm.satellite.global.exception

import kr.hs.entrydsm.satellite.global.error.GlobalCustomException
import kr.hs.entrydsm.satellite.global.error.GlobalErrorCode

object ForbiddenException : GlobalCustomException(
    GlobalErrorCode.FORBIDDEN
)

object InternalServerException : GlobalCustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)