package kr.hs.entrydsm.satellite.domain.major.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface Major {
    val id: UUID
    val name: String
}

class MajorDomain(
    override val id: UUID = UUID(0, 0),
    override val name: String
) : Major, Domain