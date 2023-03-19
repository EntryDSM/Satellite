package kr.hs.entrydsm.repo.domain.major.presentation.dto.response

import java.util.UUID
import kr.hs.entrydsm.repo.domain.major.persistence.Major

data class MajorVO(
    val id: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        id = major.id,
        name = major.name
    )
}