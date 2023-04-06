package kr.hs.entrydsm.satellite.domain.major.dto

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import java.util.*

data class MajorElement(
    val id: UUID,
    val name: String
) {
    constructor(major: Major) : this(
        id = major.id,
        name = major.name
    )
}