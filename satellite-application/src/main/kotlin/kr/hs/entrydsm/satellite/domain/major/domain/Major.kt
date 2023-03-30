package kr.hs.entrydsm.satellite.domain.major.domain

import java.util.UUID

data class Major(
    val id: UUID = UUID.randomUUID(),
    val name: String
)