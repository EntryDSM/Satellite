package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.domain.document.dto.ActivityRequest
import org.hibernate.validator.constraints.Length
import java.util.*


data class UpdateActivityWebRequest(
    @field:Size(max = 10)
    val activityList: List<ActivityWebRequest>
) {
    data class ActivityWebRequest(

        override val elementId: UUID?,

        @field:Length(max = 30)
        @field:NotEmpty
        override val name: String,

        override val date: Date,

        @field:Length(max = 200)
        override val description: String?

    ) : ActivityRequest(elementId, name, date, description)
}