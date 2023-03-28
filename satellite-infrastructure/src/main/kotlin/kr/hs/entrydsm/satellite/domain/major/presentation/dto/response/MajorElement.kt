package kr.hs.entrydsm.satellite.domain.major.presentation.dto.response

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.major.persistence.Major

data class MajorElement(
    val majorId: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        majorId = major.id,
        name = major.name
    )
}