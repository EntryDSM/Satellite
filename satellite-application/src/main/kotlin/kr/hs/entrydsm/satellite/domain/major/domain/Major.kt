package kr.hs.entrydsm.satellite.domain.major.domain

import java.util.UUID

open class Major(
    open val id: UUID = UUID.randomUUID(),
    open val name: String
)