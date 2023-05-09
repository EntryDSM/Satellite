package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.domain.document.dto.ProjectRequest
import org.hibernate.validator.constraints.Length
import java.util.*

data class UpdateProjectWebRequest(
    @field:Size(max = 5)
    val projectList: List<ProjectWebRequest>
) {
    data class ProjectWebRequest(

        override val elementId: UUID?,

        @field:Length(max = 30)
        override val name: String,

        @field:Length(max = 255)
        override val representImagePath: String?,

        override val startDate: Date,

        override val endDate: Date,

        @field:Size(max = 14)
        override val skillList: List<String>,

        override val description: String,

        @field:Length(max = 225)
        override val url: String?

    ) : ProjectRequest(
        elementId, name, representImagePath, startDate, endDate, skillList, description, url
    )
}