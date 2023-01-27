package kr.hs.entrydsm.exit.domain.feedback.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object FeedbackNotFoundException : DomainCustomException(
    DomainErrorCode.FEEDBACK_NOT_FOUND
)