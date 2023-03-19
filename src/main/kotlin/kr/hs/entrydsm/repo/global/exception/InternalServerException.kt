package kr.hs.entrydsm.repo.global.exception

import kr.hs.entrydsm.repo.global.error.GlobalErrorCode
import kr.hs.entrydsm.repo.global.error.custom.GlobalCustomException

object InternalServerException: GlobalCustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)