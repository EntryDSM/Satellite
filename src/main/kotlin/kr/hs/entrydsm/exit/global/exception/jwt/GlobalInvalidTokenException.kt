package kr.hs.entrydsm.exit.global.exception.jwt

import kr.hs.entrydsm.exit.global.error.GlobalErrorCode
import kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException

object GlobalInvalidTokenException: kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException(
    kr.hs.entrydsm.exit.global.error.GlobalErrorCode.INVALID_JWT
)