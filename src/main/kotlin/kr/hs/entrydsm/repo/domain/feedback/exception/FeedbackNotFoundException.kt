package kr.hs.entrydsm.repo.domain.feedback.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object FeedbackNotFoundException : DomainCustomException(
    DomainErrorCode.FEEDBACK_NOT_FOUND
)