package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.exit.domain.major.persistence.Major
import kr.hs.entrydsm.exit.global.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern
import java.util.*

data class UpdateWriterInfoRequest (

    @field:Length(max=255)
    val profileImagePath: String,

    val majorId: UUID,
    
    val email: String,

    @field:Length(min=1, max=1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val grade: String,

    @field:Length(min=1, max=1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val classNum: String,

    @field:Length(min=2, max=2)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val number: String

) {
    fun toElement(writer: WriterInfoElement, major: Major): WriterInfoElement {
        return WriterInfoElement(
            studentId = writer.studentId,
            name = writer.name,
            profileImagePath = profileImagePath,
            grade = grade,
            classNum = classNum,
            number = number,
            email = email,
            majorId = major.id,
            majorName = major.name
        )
    }
}