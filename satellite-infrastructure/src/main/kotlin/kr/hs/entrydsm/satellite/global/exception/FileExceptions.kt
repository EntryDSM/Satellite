package kr.hs.entrydsm.satellite.global.exception

import kr.hs.entrydsm.satellite.common.error.GlobalCustomException
import kr.hs.entrydsm.satellite.common.error.GlobalErrorCode

object ImageNotFoundException : GlobalCustomException(
    GlobalErrorCode.IMAGE_NOT_FOUND
)

object InvalidExtensionException : GlobalCustomException(
    GlobalErrorCode.INVALID_EXTENSION
)