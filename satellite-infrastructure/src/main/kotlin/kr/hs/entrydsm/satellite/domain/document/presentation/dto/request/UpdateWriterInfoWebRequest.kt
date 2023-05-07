package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.Max
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import java.util.*

class UpdateWriterInfoWebRequest(

    override val majorId: UUID,

    @field:Email
    override val email: String,

    @field:Max(3)
    override val grade: Int,

    @field:Max(4)
    override val classNum: Int,

    @field:Max(20)
    override val number: Int,

) : WriterInfoRequest(majorId, email, grade, classNum, number)