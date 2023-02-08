package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import kr.hs.entrydsm.exit.domain.document.persistence.element.ProjectElement
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import java.util.*

data class UpdateProjectRequest(

    
    val projectList: List<ProjectRequest>
) {
    data class ProjectRequest(

        @field:Length(max=30)
        
        val name: String,

        @field:Length(max=255)
        
        val representImagePath: String,

        
        val startDate: Date,

        
        val endDate: Date,

        
        val skillList: List<String>,

        
        val description: String,

        @field:Length(max=225)
        val url: String?
    ) {
        fun toProjectElement() =  ProjectElement(
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
