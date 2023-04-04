package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.document.dto.ProjectRequest
import org.hibernate.validator.constraints.Length
import java.util.*

data class UpdateProjectWebRequest(
    val projectList: List<ProjectWebRequest>
) {
    data class ProjectWebRequest(

        @field:Length(max = 30)
        override val name: String,

        @field:Length(max = 255)
        override val representImagePath: String?,

        override val startDate: Date,

        override val endDate: Date,

        override val skillList: List<String>,

        override val description: String,

        @field:Length(max = 225)
        override val url: String?

    ) : ProjectRequest(
        name, representImagePath, startDate, endDate, skillList, description, url
    )
}