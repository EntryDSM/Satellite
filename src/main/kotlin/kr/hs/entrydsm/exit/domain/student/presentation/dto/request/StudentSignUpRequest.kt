package kr.hs.entrydsm.exit.domain.student.presentation.dto.request

import kr.hs.entrydsm.exit.global.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern
import javax.validation.constraints.NotBlank

data class StudentSignUpRequest (

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val profileImagePath: String,

    @field:Length(min=1, max=1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val grade: String,

    @field:Length(min=1, max=1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val classNum: String,

    @field:Length(min=2, max=2)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    @field:NotBlank
    val number: String
)