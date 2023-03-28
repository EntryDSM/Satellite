package kr.hs.entrydsm.satellite.domain.feedback.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object FeedbackNotFoundException : DomainCustomException(
    DomainErrorCode.FEEDBACK_NOT_FOUND
)