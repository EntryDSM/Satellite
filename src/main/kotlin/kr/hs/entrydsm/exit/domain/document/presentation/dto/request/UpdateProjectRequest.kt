package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.ProjectElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*

data class UpdateProjectRequest(

    @field:NotNull
    val documentId: UUID,

    @field:NotNull
    val projectList: List<ProjectRequest>
) {
    data class ProjectRequest(

        @field:Length(max=30)
        @field:NotNull
        val name: String,

        @field:Length(max=255)
        @field:NotNull
        val representImagePath: String,

        @field:NotNull
        val startDate: Date,

        @field:NotNull
        val endDate: Date,

        @field:NotNull
        val skillList: List<String>,

        @field:NotNull
        val description: String,

        @field:Length(max=225)
        val url: String?
    ) {
        fun toProjectElement(): ProjectElement {
            return this.run {
                ProjectElement(
                    name = name,
                    representImagePath = representImagePath,
                    startDate = startDate,
                    endDate = endDate,
                    skillSet = skillList,
                    description = description,
                    url = url
                )
            }
        }
    }
}
