package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.domain.document.dto.ActivityRequest
import org.hibernate.validator.constraints.Length
import java.util.*


data class UpdateActivityWebRequest(
    val activityList: List<ActivityWebRequest>
) {
    class ActivityWebRequest(

        elementId: UUID?,

        @field:Length(max = 30)
        @field:NotEmpty
        override val name: String,

        date: Date,

        endDate: Date?,

        isPeriod: Boolean?,

        @field:Length(max = 500)
        override val description: String?

    ) : ActivityRequest(elementId, name, date, endDate, isPeriod ?: false, description)
}
