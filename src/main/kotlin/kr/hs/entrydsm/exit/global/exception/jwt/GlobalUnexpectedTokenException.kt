package kr.hs.entrydsm.exit.global.exception.jwt

import kr.hs.entrydsm.exit.global.error.GlobalErrorCode
import kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException

object GlobalUnexpectedTokenException: kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException(
    kr.hs.entrydsm.exit.global.error.GlobalErrorCode.UNEXPECTED_JWT
)