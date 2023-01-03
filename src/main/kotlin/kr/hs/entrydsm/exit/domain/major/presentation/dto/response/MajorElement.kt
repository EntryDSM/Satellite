package kr.hs.entrydsm.exit.domain.major.presentation.dto.response

import kr.hs.entrydsm.exit.domain.major.persistence.Major
import java.util.*

data class MajorElement(
    val majorId: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        majorId = major.id,
        name = major.name
    )
}