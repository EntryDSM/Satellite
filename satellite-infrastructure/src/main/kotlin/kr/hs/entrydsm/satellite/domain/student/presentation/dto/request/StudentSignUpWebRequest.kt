package kr.hs.entrydsm.satellite.domain.student.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.intellij.lang.annotations.Pattern
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class StudentSignUpWebRequest(

    @field:NotBlank
    val name: String,

    val profileImagePath: String?,

    @field:NotBlank
    @field:Pattern(Student.EMAIL_SUFFIX_EXP)
    val email: String,

    @field:Max(3)
    val grade: Int,

    @field:Max(4)
    val classNum: Int,

    @field:Max(20)
    val number: Int,
)