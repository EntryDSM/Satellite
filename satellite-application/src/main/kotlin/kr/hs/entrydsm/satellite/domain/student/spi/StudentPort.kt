package kr.hs.entrydsm.satellite.domain.student.spi

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.UUID

interface StudentPort {
    fun queryById(studentId: UUID): Student?
    fun queryByEmail(email: String): Student?
}