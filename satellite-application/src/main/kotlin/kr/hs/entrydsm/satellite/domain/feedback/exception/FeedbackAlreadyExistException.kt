package kr.hs.entrydsm.satellite.domain.feedback.exception

import kr.hs.entrydsm.satellite.common.exception.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object FeedbackAlreadyExistException : DomainCustomException(
    DomainErrorCode.FEEDBACK_ALREADY_EXIST
)