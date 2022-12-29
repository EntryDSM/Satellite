package kr.hs.entrydsm.exit.global.exception.jwt

object GlobalExpiredTokenException: kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException(
    kr.hs.entrydsm.exit.global.error.GlobalErrorCode.EXPIRED_JWT
)