package kr.hs.entrydsm.satellite.domain.teacher.domain

import java.util.*

open class Teacher(
    open val id: UUID = UUID.randomUUID(),
    open val accountId: String,
    open val password: String
)