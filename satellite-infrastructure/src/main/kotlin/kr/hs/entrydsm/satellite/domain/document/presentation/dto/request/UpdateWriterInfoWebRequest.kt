package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.common.util.RegexUtil
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern
import java.util.*

data class UpdateWriterInfoWebRequest(

    @field:Length(max = 255)
    override val profileImagePath: String,

    override val majorId: UUID,

    override val email: String,

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    override val grade: String,

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    override val classNum: String,

    @field:Length(min = 2, max = 2)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    override val number: String

) : WriterInfoRequest(profileImagePath, majorId, email, grade, classNum, number)