package kr.hs.entrydsm.exit.global.exception.jwt


object GlobalInvalidClaimException: kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException(
    kr.hs.entrydsm.exit.global.error.GlobalErrorCode.INVALID_CLAIM_JWT
)