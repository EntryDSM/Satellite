package kr.hs.entrydsm.repo.global.exception

import kr.hs.entrydsm.repo.global.error.GlobalErrorCode
import kr.hs.entrydsm.repo.global.error.custom.GlobalCustomException

object ForbiddenException : GlobalCustomException(
    GlobalErrorCode.FORBIDDEN
)