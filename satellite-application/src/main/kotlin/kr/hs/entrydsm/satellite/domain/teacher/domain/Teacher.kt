package kr.hs.entrydsm.satellite.domain.teacher.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.util.*

interface Teacher {
    val id: UUID
    val accountId: String
    val password: String
}

class TeacherDomain(
    override val id: UUID = UUID(0, 0),
    override val accountId: String,
    override val password: String
) : Teacher, Domain