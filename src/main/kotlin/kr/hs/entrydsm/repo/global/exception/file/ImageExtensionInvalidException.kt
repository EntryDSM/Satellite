package kr.hs.entrydsm.repo.global.exception.file

import kr.hs.entrydsm.repo.global.error.GlobalErrorCode
import kr.hs.entrydsm.repo.global.error.custom.GlobalCustomException

object ImageExtensionInvalidException: GlobalCustomException(
    GlobalErrorCode.IMAGE_EXTENSION_INVALID
)