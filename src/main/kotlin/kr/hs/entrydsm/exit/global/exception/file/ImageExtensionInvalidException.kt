package kr.hs.entrydsm.exit.global.exception.file

import kr.hs.entrydsm.exit.global.error.GlobalErrorCode
import kr.hs.entrydsm.exit.global.error.custom.GlobalCustomException

object ImageExtensionInvalidException: GlobalCustomException(
    GlobalErrorCode.IMAGE_EXTENSION_INVALID
)