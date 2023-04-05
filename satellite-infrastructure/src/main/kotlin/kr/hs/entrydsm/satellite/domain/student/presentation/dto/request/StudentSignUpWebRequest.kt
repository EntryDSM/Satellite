package kr.hs.entrydsm.satellite.domain.student.presentation.dto.request

import kr.hs.entrydsm.satellite.common.util.RegexUtil
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern
import javax.validation.constraints.NotBlank

data class StudentSignUpWebRequest(

    @field:NotBlank
    val name: String,

    val profileImagePath: String?,

    @field:NotBlank
    @field:Pattern(Student.EMAIL_SUFFIX_EXP)
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