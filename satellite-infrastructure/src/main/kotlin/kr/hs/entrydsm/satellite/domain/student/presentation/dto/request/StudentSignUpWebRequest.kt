package kr.hs.entrydsm.satellite.domain.student.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern
import java.util.*

data class StudentSignUpWebRequest(

    @field:NotBlank
    @field:Length(max = 300)
    val name: String,

    @field:Length(max = 300)
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

    val majorId: UUID
)