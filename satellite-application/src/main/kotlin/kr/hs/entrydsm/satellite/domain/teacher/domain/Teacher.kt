package kr.hs.entrydsm.satellite.domain.teacher.domain

import java.util.UUID

data class Teacher(
    val id: UUID = UUID.randomUUID(),
    val accountId: String,
    val password: String
)