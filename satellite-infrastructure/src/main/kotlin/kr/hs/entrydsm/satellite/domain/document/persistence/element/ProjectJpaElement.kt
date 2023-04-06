package kr.hs.entrydsm.satellite.domain.document.persistence.element

import com.querydsl.core.annotations.QueryEmbeddable
import kr.hs.entrydsm.satellite.domain.document.domain.element.ProjectElement
import java.util.*

@QueryEmbeddable
class ProjectJpaElement(
    elementId: UUID,
    name: String,
    representImagePath: String,
    startDate: Date,
    endDate: Date,
    skillSet: List<String>,
    description: String,
    url: String?
) : ProjectElement(
    elementId, name, representImagePath, startDate, endDate, skillSet, description, url
) {
    companion object {
        fun of(projectElement: ProjectElement) = projectElement.run {
            ProjectJpaElement(
                elementId, name, representImagePath, startDate, endDate, skillSet, description, url
            )
        }
    }
}
