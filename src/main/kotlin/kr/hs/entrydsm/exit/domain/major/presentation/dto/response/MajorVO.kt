package kr.hs.entrydsm.exit.domain.major.presentation.dto.response


import kr.hs.entrydsm.exit.domain.major.persistence.Major
import java.util.*
import javax.persistence.Embeddable

data class MajorVO(
    val id: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        id = major.id,
        name = major.name
    )
}