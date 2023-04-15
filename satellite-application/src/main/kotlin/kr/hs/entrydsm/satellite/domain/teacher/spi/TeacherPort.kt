package kr.hs.entrydsm.satellite.domain.teacher.spi

import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import java.util.*

interface TeacherPort {
    suspend fun queryById(teacherId: UUID): Teacher?
    suspend fun queryByAccountId(accountId: String): Teacher?
}