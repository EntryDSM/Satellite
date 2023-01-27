package kr.hs.entrydsm.exit.domain.feedback.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object FeedbackAlreadyExistException : DomainCustomException(
    DomainErrorCode.FEEDBACK_ALREADY_EXIST
)