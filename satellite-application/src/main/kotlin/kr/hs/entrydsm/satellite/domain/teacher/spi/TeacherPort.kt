package kr.hs.entrydsm.satellite.domain.teacher.spi

import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher

interface TeacherPort {
    fun queryByAccountId(accountId: String): Teacher?
}