package kr.hs.entrydsm.repo.domain.company.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import kr.hs.entrydsm.repo.global.util.RegexUtil
import org.hibernate.validator.constraints.Length

data class CompanySignUpRequest(
    @field:Length(min = 10, max = 11)
    @field:NotBlank
    val phoneNumber: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Pattern(regexp = RegexUtil.PASSWORD_EXP)
    val password: String,

    @field:NotBlank
    val managerName: String,

    @field:NotBlank
    val location: String
)