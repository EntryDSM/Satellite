package kr.hs.entrydsm.repo.domain.major.presentation.dto.response


import kr.hs.entrydsm.repo.domain.major.persistence.Major
import java.util.UUID

data class MajorVO(
    val id: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        id = major.id,
        name = major.name
    )
}