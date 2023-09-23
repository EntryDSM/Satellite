package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import org.hibernate.validator.constraints.Length
import java.util.*

class UpdateWriterInfoWebRequest(

    override val majorId: UUID?,

    @field:Email
    @field:NotEmpty
    override val email: String,

    @field:Max(3)
    @field:Min(1)
    override val grade: Int,

    @field:Max(4)
    @field:Min(1)
    override val classNum: Int,

    @field:Max(20)
    @field:Min(1)
    override val number: Int,

    skillList: List<String>?,

    @field:Length(max = 300)
    override val url: String?

) : WriterInfoRequest(majorId, email, grade, classNum, number, skillList, url)