package kr.hs.entrydsm.repo.global.exception.jwt

import kr.hs.entrydsm.repo.global.error.GlobalErrorCode
import kr.hs.entrydsm.repo.global.error.custom.GlobalCustomException

object ExpiredTokenException: GlobalCustomException(
    GlobalErrorCode.EXPIRED_JWT
)