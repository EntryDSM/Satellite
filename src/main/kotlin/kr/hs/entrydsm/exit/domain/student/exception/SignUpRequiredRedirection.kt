package kr.hs.entrydsm.exit.domain.student.exception

import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException
import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty

class SignUpRequiredRedirection(
    val email: String
) : DomainCustomException(
   RedirectInfo(email)
) {
    class RedirectInfo(val email: String): CustomErrorProperty {
        override fun status(): Int = 100
        override fun message(): String = email
        override fun code(): String = "STUDENT-100-1"
    }
}