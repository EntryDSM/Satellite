package kr.hs.entrydsm.satellite.domain.student.presentation.dto.request

import javax.validation.constraints.NotBlank
import kr.hs.entrydsm.satellite.common.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern

data class StudentSignUpRequest(

    @field:NotBlank
    val name: String,

    val profileImagePath: String?,

    @field:NotBlank
    val email: String,

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val grade: String,

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val classNum: String,

    @field:Length(min = 2, max = 2)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val number: String
)