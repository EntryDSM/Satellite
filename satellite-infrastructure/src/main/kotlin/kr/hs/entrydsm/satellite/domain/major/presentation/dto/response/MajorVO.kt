package kr.hs.entrydsm.satellite.domain.major.presentation.dto.response

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.major.persistence.Major

data class MajorVO(
    val id: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        id = major.id,
        name = major.name
    )
}