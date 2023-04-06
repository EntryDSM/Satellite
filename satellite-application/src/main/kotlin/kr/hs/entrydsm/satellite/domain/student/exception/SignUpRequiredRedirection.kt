package kr.hs.entrydsm.satellite.domain.student.exception

import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

class SignUpRequiredRedirection(
    val email: String
) : DomainCustomException(
    RedirectInfo(email)
) {
    class RedirectInfo(val email: String) : CustomErrorProperty {
        override fun status(): Int = 422
        override fun message(): String = email
        override fun code(): String = "STUDENT-422-1"
    }
}