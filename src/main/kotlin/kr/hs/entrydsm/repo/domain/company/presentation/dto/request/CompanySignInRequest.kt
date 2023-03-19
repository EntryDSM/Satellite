package kr.hs.entrydsm.repo.domain.company.presentation.dto.request

import kr.hs.entrydsm.repo.global.util.RegexUtil
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CompanySignInRequest(

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp= RegexUtil.PASSWORD_EXP)
    val password: String,

)