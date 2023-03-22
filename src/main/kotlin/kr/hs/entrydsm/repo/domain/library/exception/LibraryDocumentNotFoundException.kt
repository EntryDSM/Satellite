package kr.hs.entrydsm.repo.domain.library.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object LibraryDocumentNotFoundException : DomainCustomException(
    DomainErrorCode.LIBRARY_DOCUMENT_NOT_FOUND
)