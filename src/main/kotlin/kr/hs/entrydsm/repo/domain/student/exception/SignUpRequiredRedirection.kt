package kr.hs.entrydsm.repo.domain.student.exception

import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException
import kr.hs.entrydsm.repo.global.error.custom.CustomErrorProperty

class SignUpRequiredRedirection(
    val email: String
) : DomainCustomException(
   RedirectInfo(email)
) {
    class RedirectInfo(val email: String): CustomErrorProperty {
        override fun status(): Int = 422
        override fun message(): String = email
        override fun code(): String = "STUDENT-422-1"
    }
}