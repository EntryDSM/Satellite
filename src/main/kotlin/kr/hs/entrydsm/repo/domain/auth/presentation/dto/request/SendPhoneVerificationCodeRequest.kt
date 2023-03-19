package kr.hs.entrydsm.repo.domain.auth.presentation.dto.request

import kr.hs.entrydsm.repo.global.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern

data class SendPhoneVerificationCodeRequest(
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:Length(min = 11, max = 11)
    val phoneNumber: String
)