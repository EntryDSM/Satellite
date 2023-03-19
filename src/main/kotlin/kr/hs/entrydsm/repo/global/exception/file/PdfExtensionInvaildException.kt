package kr.hs.entrydsm.repo.global.exception.file

import kr.hs.entrydsm.repo.global.error.GlobalErrorCode
import kr.hs.entrydsm.repo.global.error.custom.GlobalCustomException


object PdfExtensionInvaildException : GlobalCustomException(
    GlobalErrorCode.PDF_EXTENSION_INVALID
)