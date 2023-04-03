package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import org.hibernate.validator.constraints.Length
import java.util.Date

data class UpdateProjectWebRequest(
    val projectList: List<ProjectWebRequest>
) {
    data class ProjectWebRequest(

        @field:Length(max = 30)
        val name: String,

        @field:Length(max = 255)
        val representImagePath: String?,

        val startDate: Date,

        val endDate: Date,

        val skillList: List<String>,

        val description: String,

        @field:Length(max = 225)
        val url: String?

    ) : ProjectRequest(
        name, representImagePath, startDate, endDate, skillList, description, url
    )
}