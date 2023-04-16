package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import org.hibernate.validator.constraints.Length
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.Max

class UpdateWriterInfoWebRequest(

    @Length(max = 255)
    profileImagePath: String?,

    override val majorId: UUID,

    @field:Email
    override val email: String,

    @field:Max(3)
    override val grade: Int,

    @field:Max(4)
    override val classNum: Int,

    @field:Max(20)
    override val number: Int,

) : WriterInfoRequest(profileImagePath, majorId, email, grade, classNum, number)