package kr.hs.entrydsm.satellite.domain.library.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object SecretMismatchException : DomainCustomException(
    DomainErrorCode.SECRET_MISMATCH
)

object LibraryDocumentNotFoundException : DomainCustomException(
    DomainErrorCode.LIBRARY_DOCUMENT_NOT_FOUND
)