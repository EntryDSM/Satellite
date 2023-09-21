package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kr.hs.entrydsm.satellite.common.validator.ElementSize
import kr.hs.entrydsm.satellite.domain.document.domain.ProjectType
import kr.hs.entrydsm.satellite.domain.document.dto.ProjectRequest
import org.hibernate.validator.constraints.Length
import java.util.*

data class UpdateProjectWebRequest(
    @field:Size(max = 5)
    val projectList: List<ProjectWebRequest>
) {
    class ProjectWebRequest(

        override val elementId: UUID?,

        @field:Length(max = 30)
        @field:NotEmpty
        override val name: String,

        @field:Length(max = 255)
        override val representImagePath: String?,

        override val startDate: Date,

        override val endDate: Date?,

        isPeriod: Boolean?,

        @field:ElementSize(max = 30)
        @field:Size(max = 14)
        override val skillList: List<String>,

        @field:Length(max = 2000)
        @field:NotEmpty
        override val description: String,

        @field:Length(max = 225)
        override val url: String?,

        urls: List<String>?,

        type: ProjectType?

    ) : ProjectRequest(
        elementId, name, representImagePath, startDate, endDate, isPeriod ?: true, skillList, description, url, urls ?: listOf(), type ?: ProjectType.TEAM
    )
}