package kr.hs.entrydsm.repo.domain.feedback.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object FeedbackAlreadyExistException : DomainCustomException(
    DomainErrorCode.FEEDBACK_ALREADY_EXIST
)