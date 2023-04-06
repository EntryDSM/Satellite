package kr.hs.entrydsm.satellite.domain.teacher.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

data class Teacher(
    val id: UUID = UUID.randomUUID(),
    val accountId: String,
    val password: String
) : Domain {
    protected constructor(): this(UUID(0,0), "", "")
}