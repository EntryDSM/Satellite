package kr.hs.entrydsm.exit.domain.auth.presentation.dto.request

import kr.hs.entrydsm.exit.global.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern

data class SendPhoneNumberCodeRequest(
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:Length(min = 11, max = 11)
    val phoneNumber: String
)