package kr.hs.entrydsm.satellite.domain.major.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

class Major(
    val id: UUID = UUID.randomUUID(),
    val name: String
) : Domain {
    protected constructor() : this(name = "")
}